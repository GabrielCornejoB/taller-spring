package com.gcornejo.springplayground.infrastructure.controllers;

import com.gcornejo.springplayground.domain.models.User;
import com.gcornejo.springplayground.domain.models.UserProfile;
import com.gcornejo.springplayground.domain.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserRepository userRepository;

    public UserProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Map<String, Object> getUserProfile(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return Collections.singletonMap("profile", new UserProfile(user.get().getName(), user.get().getCompany()));
        }
        return Collections.singletonMap("message", "No users where found with that ID");
    }

    @PutMapping
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
