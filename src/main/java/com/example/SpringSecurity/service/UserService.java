package com.example.SpringSecurity.service;

import com.example.SpringSecurity.model.User;
import com.example.SpringSecurity.objects.UserRequest;
import com.example.SpringSecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        LOGGER.info("Fetching all users");
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        LOGGER.info("Fetching user by ID: {}", id);
        return userRepository.findById(id);
    }

    public Optional<User> createUser(UserRequest userRequest) {
        LOGGER.info("Creating user: {}", userRequest.getUserName());

        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setPassword(userRequest.getPassword()); // Ensure to encrypt in real application
        user.setEmail_id(userRequest.getEmail_id());
        user.setUser_type(userRequest.getUser_type());
        user.setLoginCount(0);
//        user.setCreatedOn(LocalDateTime.now());
//        user.setUpdated(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        LOGGER.info("User created successfully: {}", savedUser.getId());
        return Optional.of(savedUser);
    }

    public Optional<User> updateUser(Long id, User userDetails) {
        LOGGER.info("Updating user with ID: {}", id);

        return userRepository.findById(id).map(user -> {
            user.setUserName(userDetails.getUserName());
            user.setPassword(userDetails.getPassword()); // Ensure to encrypt in real application
            user.setEmail_id(userDetails.getEmail_id());
            user.setUser_type(userDetails.getUser_type());
          //  user.setUpdated(LocalDateTime.now());
            User updatedUser = userRepository.save(user);
            LOGGER.info("User updated successfully: {}", updatedUser.getId());
            return updatedUser;
        });
    }

    public void deleteUser(Long id) {
        LOGGER.info("Deleting user with ID: {}", id);
        try {
            userRepository.deleteById(id);
            LOGGER.info("User deleted successfully: {}", id);
        } catch (Exception e) {
            LOGGER.error("Failed to delete user with ID: {}", id, e);
            throw e; // Propagate the exception or handle accordingly
        }
    }

}
