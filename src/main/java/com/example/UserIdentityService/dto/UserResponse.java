package com.example.UserIdentityService.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	
	 private Long id;

	    private String username;

	    private String email;

	    private String role;

	    private LocalDateTime createdAt;

	    private LocalDateTime modifiedAt;

}
