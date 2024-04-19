package com.gcornejo.springplayground.infrastructure.services;

import com.gcornejo.springplayground.domain.models.User;
import com.gcornejo.springplayground.domain.models.UserProfile;
import com.gcornejo.springplayground.domain.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserProfile getUserProfile(String token) {
        String email = this.jwtService.getEmailFromToken(token);
        User user = this.userRepository.findByEmail(email).orElseThrow();

        return new UserProfile(user.getName(), user.getCompany());
    }

    public UserProfile updateUserProfile(String token, UserProfile updatedUserProfile) {
        String email = this.jwtService.getEmailFromToken(token);
        User user = this.userRepository.findByEmail(email).orElseThrow();
        user.setName(updatedUserProfile.getName());
        user.setCompany(updatedUserProfile.getCompany());
        this.userRepository.save(user);

        return new UserProfile(user.getName(), user.getCompany());
    }

}
