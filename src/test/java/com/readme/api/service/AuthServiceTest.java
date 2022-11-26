package com.readme.api.service;

import com.readme.api.db.entity.User;
import com.readme.api.db.entity.UserRole;
import com.readme.api.rest.dto.AuthRequestDto;
import com.readme.api.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    public static final String PASSWORD = "testPassword";
    public static final String LOGIN = "testName";
    @InjectMocks
    private AuthService authService;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void testAuthenticateShouldAuthenticate() {
        AuthRequestDto authRequestDto = new AuthRequestDto(LOGIN, PASSWORD);
        User testUser = new User();
        testUser.setRole(UserRole.USER);
        when(userService.findByEmail(anyString())).thenReturn(testUser);
        String token = "token";
        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("login", LOGIN);
        expectedResult.put("token", token);

        when(jwtTokenProvider.createToken(anyString(), any(UserRole.class))).thenReturn(token);
        Map<String, String> tokenAndLogin = authService.authenticate(authRequestDto);

        assertEquals(tokenAndLogin, expectedResult);

        verify(authenticationManager).authenticate(any());
        verify(userService).findByEmail(LOGIN);
        verify(jwtTokenProvider).createToken(LOGIN, UserRole.USER);
    }


}