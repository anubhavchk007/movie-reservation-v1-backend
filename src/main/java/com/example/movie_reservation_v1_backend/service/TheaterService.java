package com.example.movie_reservation_v1_backend.service;

import com.example.movie_reservation_v1_backend.dto.theater.TheaterRequest;
import com.example.movie_reservation_v1_backend.dto.theater.TheaterResponse;
import com.example.movie_reservation_v1_backend.entity.Theater;
import com.example.movie_reservation_v1_backend.repository.ShowRepository;
import com.example.movie_reservation_v1_backend.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;
    private final ShowRepository showRepository;

    public TheaterService(TheaterRepository theaterRepository, ShowRepository showRepository) {
        this.theaterRepository = theaterRepository;
        this.showRepository = showRepository;
    }

    public TheaterResponse createTheater(TheaterRequest theaterRequest) {
        Theater theater = new Theater(theaterRequest);
        theaterRepository.save(theater);
        return new TheaterResponse(theater);
    }

    public List<TheaterResponse> getAllTheaters() {
        List<Theater> allTheaters = theaterRepository.findAll();
        List<TheaterResponse> allTheatersResponse = new ArrayList<>();
        for (Theater theater : allTheaters) {
            allTheatersResponse.add(new TheaterResponse(theater));
        }
        return allTheatersResponse;
    }

    public TheaterResponse getTheaterById(String id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater not found!"));
        return new TheaterResponse(theater);
    }
}
