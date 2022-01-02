package com.gl.userservice.exception;

public class UserDataValidationException extends RuntimeException {
    public UserDataValidationException(String message) {
        super(message);
    }
}
