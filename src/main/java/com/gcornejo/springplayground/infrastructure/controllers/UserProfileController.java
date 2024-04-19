package com.gcornejo.springplayground.infrastructure.controllers;

import com.gcornejo.springplayground.domain.models.UserProfile;
import com.gcornejo.springplayground.infrastructure.services.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@AllArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<UserProfile> getUserProfile(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authHeader) {
        return ResponseEntity.ok(this.userProfileService.getUserProfile(authHeader.substring(7)));
    }

    @PutMapping
    public ResponseEntity<UserProfile> updateUserProfile(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody UserProfile updatedUserProfile) {
        return ResponseEntity.ok(this.userProfileService.updateUserProfile(authHeader.substring(7), updatedUserProfile));
    }

}
