package com.example.curriculum.api.dto;

public record SignupRequest(
        String email,
        String password,
        String fullName) {
}
