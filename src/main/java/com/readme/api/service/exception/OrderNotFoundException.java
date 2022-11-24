package com.readme.api.service.exception;

public class OrderNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Cannot find order with id = %d";

    public OrderNotFoundException(long orderId) {
        super(MESSAGE, orderId);
    }
}
