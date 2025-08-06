package com.example.movie_reservation_v1_backend.repository;

import com.example.movie_reservation_v1_backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String usesrname);
}
