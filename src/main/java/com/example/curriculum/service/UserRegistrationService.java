package com.example.curriculum.service;

import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.curriculum.api.dto.SignupRequest;
import com.example.curriculum.persistence.entity.User;
import com.example.curriculum.persistence.repository.UserRepository;

@Service
public class UserRegistrationService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user if the email does not already exist.
     *
     * @param request The registration request containing email, password, and full name
     * @return A confirmation message after successful registration
     */
    public String registerUser(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(User.Role.STUDENT); // Default role
        user.setCreatedAt(Instant.now());

        userRepository.save(user);
        return "User registered successfully!";
    }
}
