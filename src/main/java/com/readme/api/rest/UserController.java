package com.readme.api.rest;

import com.readme.api.db.entity.User;
import com.readme.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/login")
//    public ResponseEntity<User> login(){
//
//    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody @Valid User user){
        User newUser = userService.register(user);
        return new ResponseEntity<>(newUser, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUserProfile(@PathParam("id") long id, @RequestBody @Valid User user){
        User updatedUser = userService.updateProfile(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
    }
}
