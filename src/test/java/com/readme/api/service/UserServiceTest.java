package com.readme.api.service;

import com.readme.api.db.entity.User;
import com.readme.api.db.entity.UserRole;
import com.readme.api.db.repository.UserRepository;
import com.readme.api.security.jwt.JwtTokenProvider;
import com.readme.api.service.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final String TEST_EMAIL = "john_cena@mail.ru";
    @InjectMocks
    private UserService userService;

    @Mock
    private  UserRepository userRepository;

    @Mock
    private  JwtTokenProvider jwtTokenProvider;


    @Test
    public void shouldRegisterUser(){
        User testUser = new User();
        testUser.setEmail(TEST_EMAIL);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(testUser);

        User expected = new User();
        expected.setEmail(TEST_EMAIL);
        expected.setRole(UserRole.USER);

        User result = userService.register(testUser);

        assertEquals(result, expected);

        verify(userRepository).save(result);
        verify(userRepository).findByEmail(TEST_EMAIL);
    }

    @Test
    public void shouldThrowIfUserAlreadyExistsRegisterUser(){
        User testUser = new User();
        testUser.setEmail(TEST_EMAIL);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

       assertThrows(UserAlreadyExistsException.class, () -> userService.register(testUser));
    }

}