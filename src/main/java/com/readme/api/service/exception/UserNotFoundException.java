package com.readme.api.service.exception;

public class UserNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Cannot find user for login = %s";

    public UserNotFoundException(String userLogin) {
        super(MESSAGE, userLogin);
    }

}