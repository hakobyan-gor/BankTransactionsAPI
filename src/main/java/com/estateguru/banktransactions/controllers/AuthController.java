package com.estateguru.banktransactions.controllers;

import com.estateguru.banktransactions.configs.security.TokenProvider;
import com.estateguru.banktransactions.exceptions.UnauthorizedException;
import com.estateguru.banktransactions.models.AuthTokenDTO;
import com.estateguru.banktransactions.models.dtos.ResponseModel;
import com.estateguru.banktransactions.models.dtos.auth.LoginUserDto;
import com.estateguru.banktransactions.models.dtos.auth.RegisterUserDto;
import com.estateguru.banktransactions.models.entities.user.User;
import com.estateguru.banktransactions.services.user.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("auth/")
@RestController
public class AuthController extends BaseController {

    @Lazy
    private final AuthenticationManager mAuthenticationManager;
    @Lazy
    private final TokenProvider mTokenProvider;
    @Lazy
    private final UserService mUserService;

    public AuthController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserService userService) {
        this.mAuthenticationManager = authenticationManager;
        this.mTokenProvider = tokenProvider;
        this.mUserService = userService;
    }

    @PostMapping("signIn")
    public ResponseModel<?> login(@Valid @RequestBody LoginUserDto loginUser) {
        try {
            User user = mUserService.getByUsernameInLogin(loginUser.username());
            return authenticateUser(user, loginUser.password());
        } catch (Exception e) {
            return new ResponseModel<>(false, null, "Incorrect username or password.");
        }
    }

    private ResponseModel<?> authenticateUser(User user, String password) {
        try {
            final Authentication authentication = mAuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = mTokenProvider.generateToken(authentication);
            final String roleName = user.getRole().getRoleName();
            AuthTokenDTO authTokenDTO = new AuthTokenDTO(token, roleName);
            return createResult(authTokenDTO, "Ok");
        } catch (UnauthorizedException | IllegalArgumentException e) {
            return createErrorResult(e);
        } catch (BadCredentialsException e) {
            return new ResponseModel<>(false, null, "Incorrect username or password.");
        }
    }

    @PostMapping("signUp")
    public ResponseModel<?> signUp(@Valid @RequestBody RegisterUserDto dto) {
        try {
            User user = mUserService.signUp(dto);
            return authenticateUser(user, dto.password());
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

}