package com.example.SpringSecurity.service;

import com.example.SpringSecurity.Utility.JwtUtil;
import com.example.SpringSecurity.model.User;
import com.example.SpringSecurity.objects.LoginRequest;
import com.example.SpringSecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Optional<String> authenticateUser(LoginRequest loginRequest) {
        LOGGER.info("Attempting to authenticate user: {}", loginRequest.getUserName());

        // Retrieve user by username
        Optional<User> userOptional = userRepository.findByUserName(loginRequest.getUserName());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                LOGGER.info("User authenticated successfully: {}", loginRequest.getUserName());
                String jwtToken = jwtUtil.generateToken(user);
                return Optional.of(jwtToken);
            } else {
                LOGGER.warn("Password mismatch for user: {}", loginRequest.getUserName());
            }
        } else {
            LOGGER.warn("User not found: {}", loginRequest.getUserName());
        }

        return Optional.empty();
    }
}
