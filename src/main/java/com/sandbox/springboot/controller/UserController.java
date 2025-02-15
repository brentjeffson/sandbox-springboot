package com.sandbox.springboot.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {

    private final PasswordEncoder  passwordEncoder;
    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping("/private")
    public String privateEndpoint() {
        return new String();	
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        String encodedString = passwordEncoder.encode("admin");
        return new String();
    }
}
