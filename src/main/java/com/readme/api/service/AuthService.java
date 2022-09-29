package com.readme.api.service;

import com.readme.api.db.entity.User;
import com.readme.api.rest.dto.AuthRequestDto;
import com.readme.api.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public Map<String, String> authenticate(AuthRequestDto authDto) {
        String login = authDto.getName();
        String password = authDto.getPassword();
        String passwordHash = DigestUtils.md5DigestAsHex(password.getBytes());
        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(login, passwordHash);
        authenticationManager.authenticate(authentication);
        User user = userService.findByName(login);
        String token = jwtTokenProvider.createToken(login, user.getRole());
        Map<String, String> tokenAndLogin = new HashMap<>();
        tokenAndLogin.put("login", authDto.getName());
        tokenAndLogin.put("token", token);
        return tokenAndLogin;
    }


}
