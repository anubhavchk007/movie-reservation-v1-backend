package com.example.movie_reservation_v1_backend.service;

import com.example.movie_reservation_v1_backend.dto.theater.TheaterRequest;
import com.example.movie_reservation_v1_backend.dto.theater.TheaterResponse;
import com.example.movie_reservation_v1_backend.entity.Theater;
import com.example.movie_reservation_v1_backend.exception.NotFoundException;
import com.example.movie_reservation_v1_backend.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public TheaterResponse createTheater(TheaterRequest theaterRequest) {
        Theater theater = new Theater(theaterRequest);
        theaterRepository.save(theater);
        return new TheaterResponse(theater);
    }

    public List<TheaterResponse> getAllTheaters() {
        List<Theater> allTheaters = theaterRepository.findAll();
        return allTheaters.stream()
                .map(TheaterResponse::new)
                .toList();
    }

    public TheaterResponse getTheaterById(String id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Theater doesn't exist."));
        return new TheaterResponse(theater);
    }
}
