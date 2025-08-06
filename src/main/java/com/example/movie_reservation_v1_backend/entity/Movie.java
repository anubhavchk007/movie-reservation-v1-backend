package com.example.movie_reservation_v1_backend.entity;

import com.example.movie_reservation_v1_backend.dto.movie.MovieRequest;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movies")
public class Movie {
    @Id
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    private String posterImage;
    private List<String> genre;
    @Positive
    private int durationInMinutes;

    public Movie(MovieRequest movieRequest) {
        this.title = movieRequest.title();
        this.description = movieRequest.description();
        this.posterImage = movieRequest.posterImage();
        this.genre = movieRequest.genre();
        this.durationInMinutes = movieRequest.durationInMinutes();
    }
}
