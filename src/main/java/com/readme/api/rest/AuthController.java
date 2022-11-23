package com.readme.api.rest;

import com.readme.api.db.entity.User;
import com.readme.api.rest.dto.AuthRequestDto;
import com.readme.api.service.AuthService;
import com.readme.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDto authDto) {
        Map<String, String> tokenAndLogin = authService.authenticate(authDto);
        return ResponseEntity.ok(tokenAndLogin);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid AuthRequestDto authRequestDto) {
        User user = authService.buildNewUser(authRequestDto);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
}
