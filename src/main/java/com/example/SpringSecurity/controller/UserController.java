package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.model.User;
import com.example.SpringSecurity.objects.UserRequest;
import com.example.SpringSecurity.response.CustomResponse;
import com.example.SpringSecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/app/rest/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        LOGGER.info("Received request to fetch all users");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        LOGGER.info("Received request to fetch user with ID: {}", id);
        User user = userService.getUserById(id).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) {
        LOGGER.info("Received request to create user: {}", userRequest.getUserName());
        Optional<User> createdUser = userService.createUser(userRequest);
        return createdUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
         }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        LOGGER.info("Received request to update user with ID: {}", id);
        Optional<User> updatedUser = userService.updateUser(id, userDetails);
        return updatedUser.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteUser(@PathVariable Long id) {
        LOGGER.info("Received request to delete user with ID: {}", id);
        try {
            userService.deleteUser(id);
            CustomResponse<Void> response = CustomResponse.success(200, "User deleted successfully", null);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            LOGGER.error("Failed to delete user with ID: {}", id, e);
            CustomResponse<Void> response = CustomResponse.error(500, "Failed to delete user", null);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }
    }