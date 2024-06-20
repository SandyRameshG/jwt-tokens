package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.Utility.JwtUtil;
import com.example.SpringSecurity.objects.LoginRequest;
import com.example.SpringSecurity.response.APIResponse;
import com.example.SpringSecurity.service.LoginService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/app/rest")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final JwtUtil jwtUtil;
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

    @GetMapping("/validate")
    public ResponseEntity<APIResponse> validate(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        APIResponse apiResponse = new APIResponse();

        try {
            // Extract the token from the Authorization header (format "Bearer <token>")
            String token = authorizationHeader.replace("Bearer ", "");

            // Validate the token
            if (jwtUtil.validateToken(token)) {
                Claims claims = jwtUtil.parseToken(token);
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Token is valid");
                apiResponse.setData(claims);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Invalid token");
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("An error occurred while validating the token: " + e.getMessage());
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
