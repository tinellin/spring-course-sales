package com.training.tinelli.sales.rest.controller;

import com.training.tinelli.sales.exception.BadRequestException;
import com.training.tinelli.sales.exception.NotFoundException;
import com.training.tinelli.sales.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMyException(BadRequestException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleNotFoundException(NotFoundException ex) {
        return new ApiErrors(ex.getMessage());
    }
}
