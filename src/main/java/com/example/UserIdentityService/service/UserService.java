package com.example.UserIdentityService.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.UserIdentityService.dto.CreateUserRequest;
import com.example.UserIdentityService.dto.PaginationResponse;
import com.example.UserIdentityService.dto.UpdateUserRequest;
import com.example.UserIdentityService.dto.UserResponse;
import com.example.UserIdentityService.entity.User;
import com.example.UserIdentityService.exception.UserNotFoundException;
import com.example.UserIdentityService.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest request) {

    	 User user = new User();

    	    user.setUsername(request.getUsername());
    	    user.setEmail(request.getEmail());

    	    user.setPassword(
    	            passwordEncoder.encode(request.getPassword())
    	    );

    	    user.setRole(request.getRole());

    	    User savedUser = userRepository.save(user);

    	    return convertToResponse(savedUser);
    }

    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if(user == null) {
            throw new UserNotFoundException("User not found");
        }

        return convertToResponse(user);
    }

    public PaginationResponse<UserResponse> getAllUsers(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<User> users = userRepository.findAll(pageable);

        List<UserResponse> responses = new ArrayList<>();

        for(User user : users) {
            responses.add(convertToResponse(user));
        }

        return new PaginationResponse<>(
                responses,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isFirst(),
                users.isLast()
        );
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id).orElse(null);

        if(user == null) {
            throw new UserNotFoundException("User not found");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        User updatedUser = userRepository.save(user);

        return convertToResponse(updatedUser);
    }

    public void deleteUser(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if(user == null) {
            throw new UserNotFoundException("User not found");
        }

        userRepository.delete(user);
    }

    private UserResponse convertToResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setModifiedAt(user.getModifiedAt());

        return response;
    }
}