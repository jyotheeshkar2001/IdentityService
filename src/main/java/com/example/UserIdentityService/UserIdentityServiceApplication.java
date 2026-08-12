package com.example.UserIdentityService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

public class UserIdentityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserIdentityServiceApplication.class, args);
		
		
	}

}
