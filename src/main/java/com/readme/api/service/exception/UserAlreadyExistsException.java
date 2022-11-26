package com.readme.api.service.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public static final String message = "User with email %s already exists";

    public UserAlreadyExistsException(String email) {
        super(String.format(message, email));
    }
}
