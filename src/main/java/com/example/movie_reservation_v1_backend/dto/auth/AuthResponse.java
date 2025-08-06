package com.example.movie_reservation_v1_backend.dto.auth;

public record AuthResponse(
        String token
) {
    public AuthResponse(String token) {
        this.token = token;
    }
}
