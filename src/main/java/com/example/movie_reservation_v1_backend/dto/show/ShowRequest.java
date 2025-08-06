package com.example.movie_reservation_v1_backend.dto.show;

import java.time.LocalDateTime;

public record ShowRequest(
        String movieId,
        String theaterId,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
