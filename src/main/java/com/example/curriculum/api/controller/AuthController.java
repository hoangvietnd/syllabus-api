package com.example.curriculum.api.controller;

import com.example.curriculum.api.dto.LoginRequest;
import com.example.curriculum.api.dto.LoginResponse;
import com.example.curriculum.api.dto.RefreshTokenRequest;
import com.example.curriculum.api.dto.SignupRequest;
import com.example.curriculum.service.CustomUserDetailsService;
import com.example.curriculum.service.TokenService;
import com.example.curriculum.service.UserRegistrationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
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
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(TokenService tokenService,
            AuthenticationManager authenticationManager,
            UserRegistrationService userRegistrationService,
            CustomUserDetailsService customUserDetailsService) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userRegistrationService = userRegistrationService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login attempt for email: " + loginRequest.email());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.email(),
                            loginRequest.password()));
            System.out.println("Authentication successful for: " + loginRequest.email());

            String accessToken = tokenService.generateAccessToken(authentication);
            String refreshToken = tokenService.generateRefreshToken(authentication);

            LoginResponse response = new LoginResponse(accessToken, refreshToken);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Authentication failed for " + loginRequest.email() + ": " + e.getMessage());
            throw e;
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            Jwt decodedJwt = tokenService.parseToken(refreshTokenRequest.refreshToken());
            String email = decodedJwt.getSubject();
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

            String accessToken = tokenService.generateAccessToken(authentication);
            String refreshToken = tokenService.generateRefreshToken(authentication);

            LoginResponse response = new LoginResponse(accessToken, refreshToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Invalid refresh token: " + e.getMessage());
            throw e; // Or return a specific error response
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        return userRegistrationService.registerUser(request);
    }
}
