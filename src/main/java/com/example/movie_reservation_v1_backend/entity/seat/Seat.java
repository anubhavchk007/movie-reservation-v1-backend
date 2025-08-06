package com.example.movie_reservation_v1_backend.entity.seat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    private String seatId;
    private int row;
    private int column;
    private SeatType seatType;
    private double price;
    private SeatStatus status;
}
