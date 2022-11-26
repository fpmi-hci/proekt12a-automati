package com.readme.api.service.exception;

public class AuthorNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Cannot find author with id = %d";

    public AuthorNotFoundException(long authorId) {
        super(MESSAGE, authorId);
    }
}
