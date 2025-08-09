package com.example.movie_reservation_v1_backend.controller;

import com.example.movie_reservation_v1_backend.dto.movie.MovieRequest;
import com.example.movie_reservation_v1_backend.dto.movie.MovieResponse;
import com.example.movie_reservation_v1_backend.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public MovieResponse createMovie(@RequestBody MovieRequest movieRequest) {
        return movieService.createMovie(movieRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<MovieResponse> getAllMovies() {
        return movieService.getAllMovies();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all/title")
    public List<MovieResponse> getMoviesByTitle(@RequestParam String title) {
        return movieService.getMovieByTitle(title);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all/genre")
    public List<MovieResponse> getAllMoviesByGenre(@RequestParam String genre) {
        return movieService.getAllMoviesByGenre(genre);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MovieResponse getMovieById(@PathVariable String id) {
        return movieService.getMovieById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MovieResponse updateMovieById(@PathVariable String id, @RequestBody MovieRequest movieRequest) {
        return movieService.updateMovieById(id, movieRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MovieResponse deleteMoviebyId(@PathVariable String id) {
        return movieService.deleteMovieById(id);
    }
}
