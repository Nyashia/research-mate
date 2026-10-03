package com.nyashia.resarch_mate.dto;

public record AuthResponse(
        String token,
        String email,
        long expiresAt
) {
}