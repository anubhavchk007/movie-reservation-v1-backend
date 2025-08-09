package com.example.movie_reservation_v1_backend.service;

import com.example.movie_reservation_v1_backend.dto.movie.MovieRequest;
import com.example.movie_reservation_v1_backend.dto.movie.MovieResponse;
import com.example.movie_reservation_v1_backend.entity.Movie;
import com.example.movie_reservation_v1_backend.exception.NotFoundException;
import com.example.movie_reservation_v1_backend.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieResponse createMovie(MovieRequest movieRequest) {
        Movie movie = new Movie(movieRequest);
        movieRepository.save(movie);
        return new MovieResponse(movie);
    }

    public List<MovieResponse> getAllMovies() {
        List<Movie> allMovies = movieRepository.findAll();
        return allMovies.stream()
                .map(MovieResponse::new)
                .toList();
    }

    public List<MovieResponse> getMovieByTitle(String title) {
        List<Movie> allMovies = movieRepository.findAllByTitle(title);
        return allMovies.stream()
                .map(MovieResponse::new)
                .toList();
    }

    public List<MovieResponse> getAllMoviesByGenre(String genre) {
        List<Movie> allMovies = movieRepository.findAllByGenre(genre);
        return allMovies.stream()
                .map(MovieResponse::new)
                .toList();
    }

    public MovieResponse getMovieById(String id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie not found."));
        return new MovieResponse(movie);
    }

    public MovieResponse updateMovieById(String id, MovieRequest movieRequest) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie not found."));
        movie.setTitle(movieRequest.title());
        movie.setDescription(movieRequest.description());
        movie.setGenre(movieRequest.genre());
        movie.setPosterImage(movieRequest.posterImage());
        movie.setDurationInMinutes(movieRequest.durationInMinutes());

        movieRepository.save(movie);

        return new MovieResponse(movie);
    }

    public MovieResponse deleteMovieById(String id) {
        Movie movie =  movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie not found."));
        movieRepository.deleteById(id);
        return new MovieResponse(movie);
    }

}
