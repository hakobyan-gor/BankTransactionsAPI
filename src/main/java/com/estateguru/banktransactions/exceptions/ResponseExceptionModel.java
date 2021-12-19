package com.estateguru.banktransactions.exceptions;

import org.springframework.http.HttpStatus;

public record ResponseExceptionModel(
        Boolean success,
        HttpStatus httpStatus,
        String message
) {
}