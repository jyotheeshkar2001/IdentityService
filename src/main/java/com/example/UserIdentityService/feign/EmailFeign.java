package com.example.UserIdentityService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.UserIdentityService.dto.CreateUserRequest;

@FeignClient(name = "IDENTITYEMAILSERVICE")
	public interface EmailFeign {

	    @PostMapping("/api/email/provision")
	    String sendProvisionEmail(@RequestBody CreateUserRequest request);
}
