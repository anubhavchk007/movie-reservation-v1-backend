package com.example.movie_reservation_v1_backend.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String email
) {

}
