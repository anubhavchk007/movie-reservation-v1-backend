package com.example.movie_reservation_v1_backend.entity;

import com.example.movie_reservation_v1_backend.dto.theater.TheaterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "theaters")
public class Theater {
    @Id
    private String id;
    private String name;
    private int rows;
    private int columns;

    public Theater(TheaterRequest theaterRequest) {
        this.name = theaterRequest.name();
        this.rows = theaterRequest.rows();
        this.columns = theaterRequest.columns();
    }


}
