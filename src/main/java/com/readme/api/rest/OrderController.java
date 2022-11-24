package com.readme.api.rest;

import com.readme.api.db.entity.UserOrder;
import com.readme.api.rest.dto.UserOrderRequestDto;
import com.readme.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<UserOrder> addUserOrder(@RequestBody @Valid UserOrderRequestDto order,
                                                  @RequestHeader("Authorization") String currentUserToken) {
        UserOrder addedUserOrder = orderService.addOrder(order, currentUserToken);
        return new ResponseEntity<>(addedUserOrder, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOrder> updateOrder(@RequestBody  @Valid UserOrderRequestDto userOrder,
                                                 @RequestHeader("Authorization") String currentUserToken,
                                                 @PathVariable("id") long id){
        UserOrder updateOrder = orderService.updateOrder(id, userOrder, currentUserToken);
        return new ResponseEntity<>(updateOrder, HttpStatus.ACCEPTED);
    }
}
