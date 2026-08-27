package com.example.UserIdentityService.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.UserIdentityService.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // Get JWT secret key from Config Server
    @Value("${jwt.secret-key}")
    private String secretKeyValue;

    // Get JWT expiration time from Config Server
    @Value("${jwt.expiration-time}")
    private long expirationTime;

    // Create SecretKey
    private SecretKey getSecretKey() {

        return Keys.hmacShaKeyFor(
                secretKeyValue.getBytes()
        );
    }

    // Create JWT claims
    private Map<String, Object> createClaims(User user) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        return claims;
    }

    // Generate JWT
    public String generateToken(User user) {

        return Jwts.builder()

                .claims(createClaims(user))

                .subject(user.getUsername())

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expirationTime
                        )
                )

                .signWith(getSecretKey())

                .compact();
    }

    // Extract username
    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    // Extract expiration time
    public Date extractExpiration(String token) {

        return extractAllClaims(token).getExpiration();
    }

    // Check whether token is expired
    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    // Validate token
    public boolean validateToken(
            String token,
            String username) {

        String extractedUsername =
                extractUsername(token);

        return extractedUsername.equals(username)
                && !isTokenExpired(token);
    }

    // Extract all claims
    private Claims extractAllClaims(String token) {

        return Jwts.parser()

                .verifyWith(getSecretKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }
}