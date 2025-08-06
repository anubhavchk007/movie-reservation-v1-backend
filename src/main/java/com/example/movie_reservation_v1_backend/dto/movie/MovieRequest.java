package com.example.movie_reservation_v1_backend.dto.movie;

import java.util.List;

public record MovieRequest(
        String title,
        String description,
        String posterImage,
        List<String> genre,
        int durationInMinutes
) {
}
