package com.readme.api.service;


import com.readme.api.db.entity.User;
import com.readme.api.db.entity.UserRole;
import com.readme.api.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User register(User user) {
        String email = user.getEmail();
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            throw new UserAlreadyExistsException(email);
        }
        user.setRole(UserRole.USER);
        return userRepository.save(user);
    }

    @Transactional
    public User updateProfile(long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    public User findById(long id) {
        return userRepository.getById(id);
    }

    public User findByName(String username) {
        Optional<User> optionalUser = userRepository.findByName(username);
        return optionalUser.orElseThrow(UserNotFoundException::new);
    }
}
