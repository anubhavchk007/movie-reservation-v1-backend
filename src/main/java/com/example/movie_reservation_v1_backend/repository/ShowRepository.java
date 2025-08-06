package com.example.movie_reservation_v1_backend.repository;

import com.example.movie_reservation_v1_backend.dto.show.ShowResponse;
import com.example.movie_reservation_v1_backend.entity.Show;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends MongoRepository<Show, String> {
    List<Show> findAllShowsByTheaterId(String theaterId);

    List<Show> findAllShowsByMovieId(String movieId);

    List<ShowResponse> findByStartTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
