package com.example.curriculum.api.dto;

/**
 * Represents the response for a successful login request.
 *
 * @param tokenType      The type of the token, typically "Bearer".
 * @param accessToken    The short-lived token for accessing protected resources.
 * @param refreshToken   The long-lived token for refreshing the access token.
 */
public record LoginResponse(String tokenType, String accessToken, String refreshToken) {
    public LoginResponse(String accessToken, String refreshToken) {
        this("Bearer", accessToken, refreshToken);
    }
}
