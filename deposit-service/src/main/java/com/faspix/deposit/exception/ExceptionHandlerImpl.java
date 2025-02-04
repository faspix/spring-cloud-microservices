package com.faspix.deposit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerImpl {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ExceptionResponse handleAccountNotFountException(final DepositServiceException e) {
        return new ExceptionResponse(e.getMessage());
    }

    @ExceptionHandler
    private ExceptionResponse handleGeneralException(final Exception e) {
        return new ExceptionResponse(e.getMessage());
    }

}
