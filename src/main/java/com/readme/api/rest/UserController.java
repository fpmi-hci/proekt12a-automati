package com.readme.api.rest;

import com.readme.api.db.entity.User;
import com.readme.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/self")
    public ResponseEntity<User> getSelfUser(@RequestHeader("Authorization") String currentUserToken) {
        User user = userService.findUserByToken(currentUserToken);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") long userId) {
        User user = userService.findById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity<User> updateUser(@PathVariable("id") long userId,
                                           @RequestHeader("Authorization") String currentUserToken,
                                           @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, currentUserToken, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/block/{id}")
    public ResponseEntity<User> blockUser(@PathVariable("id") long userId) {
        User user = userService.blockUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/unblock/{id}")
    public ResponseEntity<User> unblockUser(@PathVariable("id") long userId) {
        User user = userService.unblockUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
