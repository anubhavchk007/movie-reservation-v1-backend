package com.example.movie_reservation_v1_backend.controller;

import com.example.movie_reservation_v1_backend.dto.booking.BookingRequest;
import com.example.movie_reservation_v1_backend.dto.booking.BookingResponse;
import com.example.movie_reservation_v1_backend.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("booking/create")
    public BookingResponse createBooking(@RequestBody BookingRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        return bookingService.createBooking(userDetails.getUsername(), request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("booking/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("booking/revenue")
    @PreAuthorize("hasRole('ADMIN')")
    public double getTotalRevenue() {
        return bookingService.getTotalRevenue();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("booking/my-bookings")
    public List<BookingResponse> getAllBookingsOfUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return bookingService.getAllBookingsOfUser(username);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("booking/my-bookings/{bookingId}")
    public BookingResponse getBookingById(@PathVariable String bookingId, @AuthenticationPrincipal UserDetails userDetails) {
        return bookingService.getBookingById(userDetails.getUsername(), bookingId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("booking/my-bookings/{bookingId}")
    public BookingResponse cancelBookingById(@PathVariable String bookingId, @AuthenticationPrincipal UserDetails userDetails) {
        return bookingService.cancelBookingById(userDetails.getUsername(), bookingId);
    }
}
