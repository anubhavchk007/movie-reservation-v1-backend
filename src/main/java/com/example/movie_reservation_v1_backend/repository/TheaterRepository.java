package com.example.movie_reservation_v1_backend.repository;


import com.example.movie_reservation_v1_backend.entity.Theater;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TheaterRepository extends MongoRepository<Theater, String> {
}
