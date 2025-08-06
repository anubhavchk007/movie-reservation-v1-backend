package com.example.movie_reservation_v1_backend.repository;

import com.example.movie_reservation_v1_backend.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findAllByGenre(String genre);

    List<Movie> findAllByTitle(String title);
}
