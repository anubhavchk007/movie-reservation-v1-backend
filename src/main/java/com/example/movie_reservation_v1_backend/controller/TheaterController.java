package com.example.movie_reservation_v1_backend.controller;

import com.example.movie_reservation_v1_backend.dto.theater.TheaterRequest;
import com.example.movie_reservation_v1_backend.dto.theater.TheaterResponse;
import com.example.movie_reservation_v1_backend.service.TheaterService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public TheaterResponse createTheater(@RequestBody TheaterRequest theaterRequest) {
        return theaterService.createTheater(theaterRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<TheaterResponse> getALlTheaters() {
        return theaterService.getAllTheaters();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TheaterResponse getTheaterById(@PathVariable String id) {
        return theaterService.getTheaterById(id);
    }
}
