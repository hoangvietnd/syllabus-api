package com.example.curriculum.api.dto;

/**
 * Represents the request body for refreshing a token.
 *
 * @param refreshToken The refresh token sent by the client.
 */
public record RefreshTokenRequest(String refreshToken) {
}
