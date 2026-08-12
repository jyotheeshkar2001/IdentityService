package com.example.UserIdentityService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.UserIdentityService.dto.LoginRequest;
import com.example.UserIdentityService.dto.LoginResponse;
import com.example.UserIdentityService.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader("Authorization") String authorizationHeader) {

        authService.logout(authorizationHeader);

        return ResponseEntity.ok("Logout Successful");
    }

}