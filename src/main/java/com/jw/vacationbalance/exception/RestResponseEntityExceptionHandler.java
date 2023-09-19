package com.jw.vacationbalance.exception;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    /**
     *
     * @param ex an exception of type BadRequestException
     * @return an HttpEntity containing an appropriate ErrorMessage
     */
    @ExceptionHandler(value
            = {BadRequestException.class})
    protected HttpEntity<ErrorMessage> handleBadRequest(
            BadRequestException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param ex an exception of type ResourceNotFoundException
     * @return an HttpEntity containing an appropriate ErrorMessage
     */
    @ExceptionHandler(value
            = {ResourceNotFoundException.class})
    protected HttpEntity<ErrorMessage> handleResourceNotFound(
            ResourceNotFoundException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return new ResponseEntity(message, HttpStatus.NOT_FOUND);
    }
}