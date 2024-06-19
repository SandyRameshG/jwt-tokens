package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.model.User;
import com.example.SpringSecurity.objects.LoginRequest;
import com.example.SpringSecurity.objects.UserRequest;
import com.example.SpringSecurity.repository.UserRepository;
import com.example.SpringSecurity.response.CustomResponse;
import com.example.SpringSecurity.service.LoginService;
import com.example.SpringSecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@RestController
@RequestMapping("/app/rest")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        LOGGER.info("Received login request for user: {}", loginRequest.getUserName());
//
//        Optional<User> authenticatedUser = loginService.createUser(loginRequest);
//
//        if (authenticatedUser.isPresent()) {
//            LOGGER.info("Login successful for user: {}", loginRequest.getUserName());
//            return ResponseEntity.ok()
//                    .body(CustomResponse.success(200, "logged in  successfully", authenticatedUser));
//        } else {
//            LOGGER.warn("Login failed for user: {}", loginRequest.getUserName());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(CustomResponse.error(HttpStatus.NOT_FOUND.value(), "Login Failed", null));
//        }
//    }
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Optional<String> jwtToken = loginService.authenticateUser(loginRequest);
        return jwtToken.map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().body("Invalid username or password"));
    }
}
