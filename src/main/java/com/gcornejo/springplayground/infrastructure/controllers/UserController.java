package com.gcornejo.springplayground.infrastructure.controllers;

import com.gcornejo.springplayground.domain.models.LoginRequest;
import com.gcornejo.springplayground.domain.models.User;
import com.gcornejo.springplayground.domain.models.UserProfile;
import com.gcornejo.springplayground.domain.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User newUser) {
        Optional<User> user = this.userRepository.findByEmail(newUser.getEmail());
        if (user.isPresent()) {
            return Collections.singletonMap("message", "User already exists");
        }
        this.userRepository.save(newUser);
        return Collections.singletonMap("message", "User was created");
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = this.userRepository.findByEmail(loginRequest.getEmail());

        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            return Collections.singletonMap("message", "Login successfully");
        }
        return Collections.singletonMap("message", "Invalid credentials");
    }

    @GetMapping("/users/{id}")
    public Map<String, Object> getUserProfile(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return Collections.singletonMap("profile", new UserProfile(user.get().getName(), user.get().getCompany()));
        }
        return Collections.singletonMap("message", "No users where found with that ID");
    }

    @PutMapping("/users/{id}")
    public Map<String, Object> updateUserProfile(@PathVariable Long id, @RequestBody UserProfile updatedUserProfile) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setCompany(updatedUserProfile.getCompany());
            user.get().setName(updatedUserProfile.getName());
            return Collections.singletonMap("message", "User profile updated successfully");
        }
        return Collections.singletonMap("message", "User doesn't exist in the database.");
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return (List<User>) this.userRepository.findAll();
    }
}
