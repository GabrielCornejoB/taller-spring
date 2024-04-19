package com.gcornejo.springplayground.infrastructure.services;

import com.gcornejo.springplayground.domain.models.AuthResponse;
import com.gcornejo.springplayground.domain.models.LoginRequest;
import com.gcornejo.springplayground.domain.models.RegisterRequest;
import com.gcornejo.springplayground.domain.models.User;
import com.gcornejo.springplayground.domain.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    public AuthResponse login(LoginRequest request) {
        var authObject = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        this.authManager.authenticate(authObject);
        UserDetails user = this.userRepository.findByEmail(request.getEmail()).orElseThrow();

        return new AuthResponse(this.jwtService.getTokenFromUser(user));
    }


    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setCompany(request.getCompany());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        this.userRepository.save(user);

        return new AuthResponse(this.jwtService.getTokenFromUser(user));
    }
}
