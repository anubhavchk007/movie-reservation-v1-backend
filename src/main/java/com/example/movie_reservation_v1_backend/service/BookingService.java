package com.example.movie_reservation_v1_backend.service;

import com.example.movie_reservation_v1_backend.dto.booking.BookingRequest;
import com.example.movie_reservation_v1_backend.dto.booking.BookingResponse;
import com.example.movie_reservation_v1_backend.entity.Show;
import com.example.movie_reservation_v1_backend.entity.booking.Booking;
import com.example.movie_reservation_v1_backend.entity.booking.BookingStatus;
import com.example.movie_reservation_v1_backend.entity.seat.SeatStatus;
import com.example.movie_reservation_v1_backend.exception.NotFoundException;
import com.example.movie_reservation_v1_backend.exception.SeatAlreadyBookedException;
import com.example.movie_reservation_v1_backend.repository.BookingRepository;
import com.example.movie_reservation_v1_backend.repository.ShowRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;

    public BookingService(BookingRepository bookingRepository, ShowRepository showRepository) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
    }

    @Transactional
    public BookingResponse createBooking(String username, BookingRequest request) {
        try {
            checkValidRequest(request);
            Booking booking = new Booking(request);
            booking.setUsername(username);
            booking.setPrice(calculatePrice(request));
            // payment logic here
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
            return new BookingResponse(booking);
        } catch (OptimisticLockingFailureException e) {
            throw new OptimisticLockingFailureException("Seats are being booked simultaneously.");
        }
    }

    private void checkValidRequest(BookingRequest request) {
        Show show = showRepository.findById(request.showId())
                .orElseThrow(() -> new NotFoundException("Show not found."));
        for (String seatId : request.selectedSeatIds()) {
            if (show.getSeats().get(seatId).getStatus() == SeatStatus.BOOKED) {
                throw new SeatAlreadyBookedException("Selected seat(s) are already booked.");
            }
        }
    }

    private double calculatePrice(BookingRequest request) {
        Show show = showRepository.findById(request.showId())
                .orElseThrow(() -> new NotFoundException("Show not found."));
        double price = 0;
        for (String seatId : request.selectedSeatIds()) {
            price += show.getSeats().get(seatId).getPrice();
            show.getSeats().get(seatId).setStatus(SeatStatus.BOOKED);
        }
        showRepository.save(show);
        return price;
    }

    public List<BookingResponse> getAllBookingsOfUser(String username) {
        List<Booking> allBookings = bookingRepository.findAllByUsername(username);
        return allBookings.stream()
                .map(BookingResponse::new)
                .toList();
    }

    public BookingResponse getBookingById(String username, String bookingId) {
        Booking booking = bookingRepository.findByIdAndUsername(bookingId, username)
                .orElseThrow(() -> new NotFoundException("Booking not found."));
        return new BookingResponse(booking);
    }

    public BookingResponse cancelBookingById(String username, String bookingId) {
        Booking booking = bookingRepository.findByIdAndUsername(bookingId, username)
                .orElseThrow(() -> new NotFoundException("Booking not found."));
        Show show = showRepository.findById(booking.getShowId())
                .orElseThrow(() -> new NotFoundException("Show is already over, booking can't be cancelled."));
        releaseSeats(show, booking);
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        return new BookingResponse(booking);
    }

    public void releaseSeats(Show show, Booking booking) {
        for (String seatId : booking.getSelectedSeatIds()) {
            show.getSeats().get(seatId).setStatus(SeatStatus.AVAILABLE);
        }
        showRepository.save(show);
    }

    public List<BookingResponse> getAllBookings() {
        List<Booking> allBookings = bookingRepository.findAll();
        return allBookings.stream()
                .map(BookingResponse::new)
                .toList();
    }

    public double getTotalRevenue() {
        List<Booking> allBookings = bookingRepository.findAll();
        double totalRevenue = 0L;
        for (Booking booking : allBookings) {
            if (booking.getStatus() == BookingStatus.CONFIRMED) {
                totalRevenue += booking.getPrice();
            }
        }
        return totalRevenue;
    }
}
