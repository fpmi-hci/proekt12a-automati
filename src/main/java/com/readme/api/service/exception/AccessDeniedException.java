package com.readme.api.service.exception;

import com.readme.api.db.entity.User;

public abstract class AccessDeniedException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "User = %s doesn't have access to %s";


    public AccessDeniedException(User user, String deniedResource) {
        super(String.format(MESSAGE_FORMAT, user, deniedResource));
    }
}
