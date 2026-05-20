package com.accountservice.exception;

public class AadharNumberAlreadyExistsException
        extends RuntimeException {

    public AadharNumberAlreadyExistsException(String message) {
        super(message);
    }
}