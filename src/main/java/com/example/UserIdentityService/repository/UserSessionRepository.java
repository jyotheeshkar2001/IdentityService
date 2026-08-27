package com.example.UserIdentityService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.UserIdentityService.entity.UserSession;


@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
	
	List<UserSession> findByUserUsernameAndActiveTrue(String username);

}
