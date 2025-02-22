package com.sandbox.springboot.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sandbox.springboot.model.User;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }
    
    public String authenticateUser(String username, String password) {
        try {
            User user = (User) userService.loadUserByUsername(username);

            if (passwordEncoder.matches(password, user.getPassword())) { // In production, use passwordEncoder.matches()
                return jwtService.generateToken(username);
            }
            return null;
        } catch (UsernameNotFoundException e) {
            return null;
        }
    }
}
