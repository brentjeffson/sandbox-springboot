package com.sandbox.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tests")
public class TestController {
    
    @GetMapping("/")
    public ResponseEntity<Object> test(@RequestParam String param) {
        return ResponseEntity.ok().body("Success...");
    }
    
}
