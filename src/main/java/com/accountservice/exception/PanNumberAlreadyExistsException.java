package com.accountservice.exception;

public class PanNumberAlreadyExistsException
        extends RuntimeException {

    public PanNumberAlreadyExistsException(String message) {
        super(message);
    }
}