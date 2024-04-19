package com.gcornejo.springplayground.infrastructure.controllers;

import com.gcornejo.springplayground.domain.models.AuthResponse;
import com.gcornejo.springplayground.domain.models.LoginRequest;
import com.gcornejo.springplayground.domain.models.RegisterRequest;
import com.gcornejo.springplayground.infrastructure.services.AuthService;
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

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(this.authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(this.authService.register(request));
    }

}
