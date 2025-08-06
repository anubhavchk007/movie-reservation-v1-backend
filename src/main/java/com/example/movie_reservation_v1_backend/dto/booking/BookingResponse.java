package com.example.movie_reservation_v1_backend.dto.booking;

import com.example.movie_reservation_v1_backend.entity.booking.Booking;
import com.example.movie_reservation_v1_backend.entity.booking.BookingStatus;

import java.util.List;

public record BookingResponse(
        String id,
        String username,
        String showId,
        List<String> selectedSeatIds,
        double price,
        BookingStatus status
) {
    public BookingResponse(Booking booking) {
        this(booking.getId(), booking.getUsername(), booking.getShowId(), booking.getSelectedSeatIds(), booking.getPrice(), booking.getStatus());
    }
}
