package com.example.UserIdentityService.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.UserIdentityService.entity.User;
import com.example.UserIdentityService.repository.UserRepository;
import com.example.UserIdentityService.security.CustomUserDetails;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	  @Autowired
	    private UserRepository userRepository;


	    @Override
	    public UserDetails loadUserByUsername(String username)
	            throws UsernameNotFoundException {


	        User user = userRepository.findByUsername(username).orElse(null);


	        if (user == null) {

	            throw new UsernameNotFoundException("User not found");
	        }


	        return new CustomUserDetails(user);
	    }
	}


