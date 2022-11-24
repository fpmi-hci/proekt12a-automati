package com.readme.api.service.exception;

import com.readme.api.db.entity.User;
import com.readme.api.db.entity.UserOrder;

public class OrderAccessDeniedException extends AccessDeniedException {
    private static final String ORDER_RESOURCE = "Order, id = %d";

    public OrderAccessDeniedException(User user, UserOrder userOrder) {
        super(user, String.format(ORDER_RESOURCE, userOrder.getId()));
    }
}
