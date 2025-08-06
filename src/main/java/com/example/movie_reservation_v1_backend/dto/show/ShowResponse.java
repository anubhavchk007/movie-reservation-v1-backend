package com.example.movie_reservation_v1_backend.dto.show;

import com.example.movie_reservation_v1_backend.entity.Movie;
import com.example.movie_reservation_v1_backend.entity.Show;
import com.example.movie_reservation_v1_backend.entity.Theater;
import com.example.movie_reservation_v1_backend.entity.seat.Seat;

import java.time.LocalDateTime;
import java.util.Map;

public record ShowResponse(
        String id,
        Movie movie,
        Theater theater,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Map<String, Seat> seats
) {
    public ShowResponse(Show show) {
        this(show.getId(), show.getMovie(), show.getTheater(), show.getStartTime(), show.getEndTime(), show.getSeats());
    }
}
