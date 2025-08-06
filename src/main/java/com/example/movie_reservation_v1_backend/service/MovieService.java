package com.example.movie_reservation_v1_backend.service;

import com.example.movie_reservation_v1_backend.dto.movie.MovieRequest;
import com.example.movie_reservation_v1_backend.dto.movie.MovieResponse;
import com.example.movie_reservation_v1_backend.entity.Movie;
import com.example.movie_reservation_v1_backend.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<MovieResponse> allMoviesResponse = new ArrayList<>();
        for (Movie movie : allMovies) {
            allMoviesResponse.add(new MovieResponse(movie));
        }
        return allMoviesResponse;
    }

    public List<MovieResponse> getMovieByTitle(String title) {
        List<Movie> allMovies = movieRepository.findAllByTitle(title);
        List<MovieResponse> allMoviesResponse = new ArrayList<>();
        for (Movie movie : allMovies) {
            allMoviesResponse.add(new MovieResponse(movie));
        }
        return allMoviesResponse;
    }

    public List<MovieResponse> getAllMoviesByGenre(String genre) {
        List<Movie> allMovies = movieRepository.findAllByGenre(genre);
        List<MovieResponse> allMoviesResponse = new ArrayList<>();
        for (Movie movie : allMovies) {
            allMoviesResponse.add(new MovieResponse(movie));
        }
        return allMoviesResponse;
    }

    public MovieResponse getMovieById(String id) {
        Movie movie =  movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie Not found"));
        return new MovieResponse(movie);
    }

    public MovieResponse updateMovieById(String id, MovieRequest movieRequest) {
        Movie movie =  movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie Not found"));
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
                .orElseThrow(() -> new RuntimeException("Movie Not found"));
        movieRepository.deleteById(id);
        return new MovieResponse(movie);
    }

}
