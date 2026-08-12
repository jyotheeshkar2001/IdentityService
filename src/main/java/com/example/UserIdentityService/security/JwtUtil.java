package com.example.UserIdentityService.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.example.UserIdentityService.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkeymysecretkey";

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60 * 24;

    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    private Map<String, Object> createClaims(User user) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        return claims;
    }
    
    
    
    

    public String generateToken(User user) {

        return Jwts.builder()
                .claims(createClaims(user))
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }



    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }



    public Date extractExpiration(String token) {

        return extractAllClaims(token).getExpiration();
    }



    public boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }



    public boolean validateToken(String token, String username) {

        String extractedUsername = extractUsername(token);

        return extractedUsername.equals(username)
                && !isTokenExpired(token);
    }



    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}