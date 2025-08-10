package com.example.movie_reservation_v1_backend.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String email;
    private Role role;
}
