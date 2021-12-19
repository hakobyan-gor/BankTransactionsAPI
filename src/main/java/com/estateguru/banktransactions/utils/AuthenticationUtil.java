package com.estateguru.banktransactions.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}