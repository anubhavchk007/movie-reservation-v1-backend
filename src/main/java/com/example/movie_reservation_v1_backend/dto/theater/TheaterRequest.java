package com.example.movie_reservation_v1_backend.dto.theater;

public record TheaterRequest(
        String name,
        int rows,
        int columns
) {
}
