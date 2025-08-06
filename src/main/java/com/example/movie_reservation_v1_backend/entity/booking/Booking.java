package com.example.movie_reservation_v1_backend.entity.booking;

import com.example.movie_reservation_v1_backend.dto.booking.BookingRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;
    private String username;
    private String showId;
    private List<String> selectedSeatIds;
    private double price;
    private BookingStatus status;

    public Booking(BookingRequest request) {
        this.username = request.username();
        this.showId = request.showId();
        this.selectedSeatIds = request.selectedSeatIds();
        status = BookingStatus.PENDING;
    }
}
