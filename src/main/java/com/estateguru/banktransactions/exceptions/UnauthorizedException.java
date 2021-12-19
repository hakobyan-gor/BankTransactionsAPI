package com.estateguru.banktransactions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UnauthorizedException extends UsernameNotFoundException {

    public UnauthorizedException(String message) {
        super(message);
        unauthorizedException(message);
    }

    public ResponseExceptionModel unauthorizedException(String message) {
        return new ResponseExceptionModel(false, HttpStatus.UNAUTHORIZED, message);
    }

}