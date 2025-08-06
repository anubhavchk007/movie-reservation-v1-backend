package com.example.movie_reservation_v1_backend.dto.theater;

import com.example.movie_reservation_v1_backend.entity.Theater;

public record TheaterResponse(
        String id,
        String name,
        int rows,
        int columns
) {
    public TheaterResponse(Theater theater) {
        this(theater.getId(), theater.getName(), theater.getRows(), theater.getColumns());
    }
}
