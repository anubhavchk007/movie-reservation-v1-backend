package com.example.movie_reservation_v1_backend.service;

import com.example.movie_reservation_v1_backend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private static final String SECRET_BASE64 = "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef";

    private SecretKey secretKey;
    private JwtParser parser;

    @PostConstruct
    public void init() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(SECRET_BASE64);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
        parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
    }

    public String generateToken(User user) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 1000L * 60 * 60 * 300))  // 10 hrs
                .addClaims(Map.of("role", user.getRole().name()))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        Jws<Claims> jws = parser.parseSignedClaims(token);
        return jws.getPayload().getSubject();
    }

    public String extractRole(String token) {
        Jws<Claims> jws = parser.parseSignedClaims(token);
        Object role = jws.getPayload().get("role");
        return role != null ? role.toString() : null;
    }
}
