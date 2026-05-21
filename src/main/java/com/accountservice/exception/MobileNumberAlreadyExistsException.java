package com.accountservice.exception;

public class MobileNumberAlreadyExistsException
        extends RuntimeException {

    public MobileNumberAlreadyExistsException(String message) {
        super(message);
    }
}