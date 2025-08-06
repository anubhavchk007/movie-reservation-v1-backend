package com.example.movie_reservation_v1_backend.repository;

import com.example.movie_reservation_v1_backend.entity.booking.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findAllByUsername(String username);

    Optional<Booking> findByIdAndUsername(String bookingId, String username);
}
