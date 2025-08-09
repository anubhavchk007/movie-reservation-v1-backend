package com.example.movie_reservation_v1_backend.controller;

import com.example.movie_reservation_v1_backend.dto.show.ShowRequest;
import com.example.movie_reservation_v1_backend.dto.show.ShowResponse;
import com.example.movie_reservation_v1_backend.service.ShowService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("shows/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ShowResponse createShow(@RequestBody ShowRequest showRequest) {
        return showService.createShow(showRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("shows/get-all")
    public List<ShowResponse> getAllShows() {
        return showService.getAllShows();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("shows/{id}")
    public ShowResponse getShowById(@PathVariable String id) {
        return showService.getShowById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("shows/movie/{movieId}")
    public List<ShowResponse> getAllShowsByMovie(@PathVariable String movieId) {
        return showService.getAllShowsByMovie(movieId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("shows/theater/{theaterId}")
    public List<ShowResponse> getAllShowsByTheater(@PathVariable String theaterId) {
        return showService.getAllShowsByTheater(theaterId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("shows/by-date")
    public List<ShowResponse> getAllShowsByDate(@RequestParam String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        return showService.getAllShowsByDate(date);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("shows/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ShowResponse updateShowById(@PathVariable String id, @RequestBody ShowRequest showRequest) {
        return showService.updateShowById(id, showRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("shows/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ShowResponse deleteShowById(@PathVariable String id) {
        return showService.deleteShowById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("shows/{id}/available-seats")
    public List<String> getAvailableSeats(@PathVariable String id) {
        return showService.getAvailableSeats(id);
    }
}
