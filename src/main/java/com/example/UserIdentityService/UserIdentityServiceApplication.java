package com.example.UserIdentityService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.UserIdentityService.feign")
public class UserIdentityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserIdentityServiceApplication.class, args);
		
		
	}

}
