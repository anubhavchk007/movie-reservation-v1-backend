package com.example.movie_reservation_v1_backend.dto.booking;

import java.util.List;

public record BookingRequest(
        String username,
        String showId,
        List<String> selectedSeatIds
) {
}
