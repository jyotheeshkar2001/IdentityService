package com.example.UserIdentityService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.UserIdentityService.dto.LoginRequest;
import com.example.UserIdentityService.dto.LoginResponse;
import com.example.UserIdentityService.entity.User;
import com.example.UserIdentityService.exception.UserNotFoundException;
import com.example.UserIdentityService.repository.UserRepository;
import com.example.UserIdentityService.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionService userSessionService;


    public LoginResponse login(LoginRequest request) {


        // Check username exists
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));


        try {

            // Validate password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );


            // Save successful login session
            userSessionService.saveLoginSuccess(user);


            // Generate JWT
            String token = jwtUtil.generateToken(user);


            return new LoginResponse(token);


        } catch (BadCredentialsException e) {


            // Save failed login attempt
            userSessionService.saveLoginFailure(user);


            throw e;
        }
    }


    public void logout(String authorizationHeader) {


        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            throw new RuntimeException("Invalid token");
        }


        String token = authorizationHeader.substring(7);


        String username = jwtUtil.extractUsername(token);


        userSessionService.logout(username);
    }

}