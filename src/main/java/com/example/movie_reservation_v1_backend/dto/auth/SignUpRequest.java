package com.example.movie_reservation_v1_backend.dto.auth;

public record SignUpRequest(
        String username,
        String password,
        String email
) {

}
