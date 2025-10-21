package com.example.curriculum.api.controller;

import com.example.curriculum.api.dto.LoginRequest;
import com.example.curriculum.api.dto.SignupRequest;
import com.example.curriculum.service.TokenService;
import com.example.curriculum.service.UserRegistrationService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRegistrationService userRegistrationService;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager,
            UserRegistrationService userRegistrationService) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()));
        return tokenService.generateToken(authentication);
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        return userRegistrationService.registerUser(request);
    }
}
