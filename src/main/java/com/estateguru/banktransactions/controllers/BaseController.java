package com.estateguru.banktransactions.controllers;

import com.estateguru.banktransactions.models.dtos.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public class BaseController {

    protected <R> ResponseModel<R> createResult(R data, String message) {
        return new ResponseModel<>(true, data, message);
    }

    protected <R> ResponseModel<R> createErrorResult(Exception e) {
        return new ResponseModel<>(false, null, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <R> ResponseModel<R> handleException(MethodArgumentNotValidException exception) {
        return new ResponseModel<>(false, null, getFieldError(exception.getBindingResult().getFieldErrors()));
    }

    private String getFieldError(List<FieldError> fieldErrors) {
        if (!fieldErrors.isEmpty()) {
            FieldError fieldError = fieldErrors.get(0);
            return String.format("Field '%s' %s", fieldError.getField(), fieldError.getDefaultMessage());
        }
        return "Validation error";
    }

}