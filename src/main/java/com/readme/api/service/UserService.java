package com.readme.api.service;


import com.readme.api.db.entity.User;
import com.readme.api.db.entity.UserRole;
import com.readme.api.db.repository.UserRepository;
import com.readme.api.security.jwt.JwtTokenProvider;
import com.readme.api.service.exception.UserAlreadyExistsException;
import com.readme.api.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

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

    @Transactional
    public User findById(long id) {
        return userRepository.getById(id);
    }

    public User findByName(String username) {
        Optional<User> optionalUser = userRepository.findByName(username);
        return optionalUser.orElseThrow(() -> new UserNotFoundException(username));
    }

    public User findUserByToken(String currentUserToken) {
        String login = jwtTokenProvider.getLogin(currentUserToken);
        return findByName(login);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User blockUser(long userId) {
        User userToBlock = findById(userId);
        userToBlock.setBlocked(true);
        return userRepository.save(userToBlock);
    }


    public User unblockUser(long userId) {
        User userToUnblock = findById(userId);
        userToUnblock.setBlocked(false);
        return userRepository.save(userToUnblock);
    }

    public User updateUser(long userId, String currentUserToken, User newUser) {
        User currentUser = findUserByToken(currentUserToken);
        if (!isAdminOrSelfUpdate(userId, currentUser)) {
            throw new AccessDeniedException("Users can't change other users");
        }
        newUser.setId(userId);
        return userRepository.save(newUser);
    }

    private static boolean isAdminOrSelfUpdate(long userId, User currentUser) {
        return currentUser.getRole() == UserRole.ADMIN || (currentUser.getId() == userId);
    }
}
