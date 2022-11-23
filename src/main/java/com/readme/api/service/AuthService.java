package com.readme.api.service;

import com.readme.api.db.entity.User;
import com.readme.api.db.entity.UserRole;
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

    private static final String NAME_FROM_EMAIL_END = "@";

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public Map<String, String> authenticate(AuthRequestDto authDto) {
        String email = authDto.getEmail();
        String password = authDto.getPassword();
        String passwordHash = DigestUtils.md5DigestAsHex(password.getBytes());
        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, passwordHash);
        authenticationManager.authenticate(authentication);
        User user = userService.findByEmail(email);
        String token = jwtTokenProvider.createToken(email, user.getRole());
        Map<String, String> tokenAndLogin = new HashMap<>();
        tokenAndLogin.put("login", authDto.getEmail());
        tokenAndLogin.put("token", token);
        return tokenAndLogin;
    }


    public User buildNewUser(AuthRequestDto authRequestDto) {
        String name = getFromEmail(authRequestDto.getEmail());
        User newUser = User.builder()
                .name(name)
                .email(authRequestDto.getEmail())
                .role(UserRole.USER)
                .isBlocked(false)
                .password(DigestUtils.md5DigestAsHex(authRequestDto.getPassword().getBytes()))
                .build();
        return userService.register(newUser);
    }

    private String getFromEmail(String email) {
        return email.split(NAME_FROM_EMAIL_END)[0];
    }
}
