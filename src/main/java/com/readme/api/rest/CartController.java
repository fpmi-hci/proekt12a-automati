package com.readme.api.rest;

import com.readme.api.rest.dto.CartResponseDto;
import com.readme.api.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<CartResponseDto> getCartForCurrentUser(@RequestHeader("Authorization") String currentUserToken) {
        CartResponseDto cart = cartService.getCartForCurrentUser(currentUserToken);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("remove/{bookId}")
    public ResponseEntity<CartResponseDto> removeFromCart(@PathVariable("bookId") long bookId,
                                                          @RequestHeader("Authorization") String currentUserToken){
        CartResponseDto cart = cartService.removeFromCart(bookId, currentUserToken);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/batch/remove")
    public ResponseEntity<CartResponseDto> batchRemoveFromCart(@RequestBody List<Long> bookIds,
                                                               @RequestHeader("Authorization") String currentUserToken){
        CartResponseDto cart = cartService.removeFromCartBatch(bookIds, currentUserToken);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("add/{bookId}")
    public ResponseEntity<CartResponseDto> addToCart(@PathVariable("bookId") long bookId,
                                                     @RequestHeader("Authorization") String currentUserToken){
        CartResponseDto cart = cartService.addToCart(bookId, currentUserToken);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/batch/add")
    public ResponseEntity<CartResponseDto> batchAddToCart(@RequestBody List<Long> bookIds,
                                                          @RequestHeader("Authorization") String currentUserToken){
        CartResponseDto cart = cartService.addToCartBatch(bookIds, currentUserToken);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

}
