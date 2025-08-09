package com.example.movie_reservation_v1_backend.controller;

import com.example.movie_reservation_v1_backend.dto.auth.AuthResponse;
import com.example.movie_reservation_v1_backend.dto.auth.LoginRequest;
import com.example.movie_reservation_v1_backend.dto.auth.SignUpRequest;
import com.example.movie_reservation_v1_backend.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public AuthResponse register(@RequestBody SignUpRequest request) {
        boolean ok = true;
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.authenticate(request);
    }
}
