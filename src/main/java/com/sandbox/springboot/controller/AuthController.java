package com.sandbox.springboot.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sandbox.springboot.dto.LoginRequest;
import com.sandbox.springboot.service.AuthenticationService;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestBody(required = true) LoginRequest loginRequest) {
        try {
            if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
                return ResponseEntity.badRequest().body("Username and password are required");
            }

            String token = authenticationService.authenticateUser(
                loginRequest.getUsername().trim(),
                loginRequest.getPassword()
            );

            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(401).body("Invalid credentials");
            }

            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Authentication failed: " + e.getMessage());
        }
    }

}