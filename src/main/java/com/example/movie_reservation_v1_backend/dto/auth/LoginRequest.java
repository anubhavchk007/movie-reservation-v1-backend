package com.example.movie_reservation_v1_backend.dto.auth;

public record LoginRequest(
        String username,
        String password
) {
}
