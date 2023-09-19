package com.jw.vacationbalance.exception;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}