package com.estateguru.banktransactions.configs.security;

import com.estateguru.banktransactions.exceptions.UnauthorizedException;
import com.estateguru.banktransactions.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    private UserService userService;

    @Value("${security.jwt.token.header-key}")
    private String headerKey;
    @Value("${security.jwt.token.prefix}")
    private String tokenPrefix;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException, UnauthorizedException {
        String header = req.getHeader(headerKey);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(tokenPrefix)) {
            authToken = header.replace(tokenPrefix, "");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (Exception e) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            try {
                userDetails = userService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, userDetails);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
        }
        chain.doFilter(req, res);
    }

}