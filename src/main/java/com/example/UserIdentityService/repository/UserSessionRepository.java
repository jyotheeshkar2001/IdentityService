package com.example.UserIdentityService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.UserIdentityService.entity.UserSession;


@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
	
	Optional<UserSession> findByUserUsernameAndActiveTrue(String username);

}
