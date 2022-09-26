package com.readme.api.rest;

import com.readme.api.db.entity.Cart;
import com.readme.api.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCartForCurrentUser(@RequestHeader("Authorization") String currentUserToken) {
        Cart cart = cartService.getCartForCurrentUser(currentUserToken);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    public ResponseEntity<Cart> removeFromCart(long bookId, @RequestHeader("Authorization") String currentUserToken){
        Cart cart = cartService.removeFromCart(bookId, currentUserToken);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    public ResponseEntity<Cart> batchRemoveFromCart(List<Long> bookIds, @RequestHeader("Authorization") String currentUserToken){
        Cart cart = cartService.removeFromCartBatch(bookIds, currentUserToken);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    public ResponseEntity<Cart> addToCart(long bookId, @RequestHeader("Authorization") String currentUserToken){
        Cart cart = cartService.addToCart(bookId, currentUserToken);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    public ResponseEntity<Cart> batchAddToCart(List<Long> bookIds, @RequestHeader("Authorization") String currentUserToken){
        Cart cart = cartService.addToCartBatch(bookIds, currentUserToken);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

}
