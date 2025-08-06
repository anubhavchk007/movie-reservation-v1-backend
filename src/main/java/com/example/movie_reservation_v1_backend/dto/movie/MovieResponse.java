package com.example.movie_reservation_v1_backend.dto.movie;

import com.example.movie_reservation_v1_backend.entity.Movie;

import java.util.List;

public record MovieResponse(
        String id,
        String title,
        String description,
        String posterImage,
        List<String> genre,
        int durationInMinutes
) {
    public MovieResponse(Movie movie) {
        this(movie.getId(), movie.getTitle(), movie.getDescription(), movie.getPosterImage(), movie.getGenre(), movie.getDurationInMinutes());
    }
}
