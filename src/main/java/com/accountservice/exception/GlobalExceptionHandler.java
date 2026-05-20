package com.accountservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        String errorMessage = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return new ResponseEntity<>(
                errorMessage,
                HttpStatus.BAD_REQUEST
        );
    }

    // Handle duplicate email exception
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailException(
            EmailAlreadyExistsException ex
    ) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    // Handle duplicate mobile number exception
    @ExceptionHandler(MobileNumberAlreadyExistsException.class)
    public ResponseEntity<String> handleMobileException(
            MobileNumberAlreadyExistsException ex
    ) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    // Handle duplicate PAN number exception
    @ExceptionHandler(PanNumberAlreadyExistsException.class)
    public ResponseEntity<String> handlePanException(
            PanNumberAlreadyExistsException ex
    ) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    // Handle duplicate Aadhar number exception
    @ExceptionHandler(AadharNumberAlreadyExistsException.class)
    public ResponseEntity<String> handleAadharException(
            AadharNumberAlreadyExistsException ex
    ) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    // Handle account not found exception
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountException(
            AccountNotFoundException ex
    ) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(
            Exception ex
    ) {

            return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}