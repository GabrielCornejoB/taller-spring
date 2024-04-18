package com.gcornejo.springplayground.infrastructure.controllers;

import com.gcornejo.springplayground.domain.models.AuthResponse;
import com.gcornejo.springplayground.domain.models.LoginRequest;
import com.gcornejo.springplayground.domain.models.RegisterRequest;
import com.gcornejo.springplayground.domain.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(new AuthResponse("Login test"));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(new AuthResponse("Register test"));
    }

}
