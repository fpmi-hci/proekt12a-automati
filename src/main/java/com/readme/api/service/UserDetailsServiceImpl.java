package com.readme.api.service;

import com.readme.api.db.entity.User;
import com.readme.api.db.repository.UserRepository;
import com.readme.api.mapper.UserMapper;
import com.readme.api.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(username);
        return user.map(userMapper::entityToSecurityUser).orElseThrow(() -> new UserNotFoundException(username));
    }
}
