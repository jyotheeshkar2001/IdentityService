package com.example.UserIdentityService.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.UserIdentityService.entity.User;
import com.example.UserIdentityService.entity.UserSession;
import com.example.UserIdentityService.repository.UserSessionRepository;

@Service
public class UserSessionService {

    @Autowired
    private UserSessionRepository userSessionRepository;

    public void saveLoginSuccess(User user) {

        UserSession session = new UserSession();

        session.setUser(user);
        session.setLoginTime(LocalDateTime.now());
        session.setStatus("SUCCESS");
        session.setActive(true);

        userSessionRepository.save(session);
    }

    public void saveLoginFailure(User user) {

        UserSession session = new UserSession();

        session.setUser(user);
        session.setLoginTime(LocalDateTime.now());
        session.setStatus("FAILED");
        session.setActive(false);

        userSessionRepository.save(session);
    }

    public void logout(String username) {

        UserSession session = userSessionRepository
                .findByUserUsernameAndActiveTrue(username)
                .orElseThrow(() -> new RuntimeException("No active session found"));

        session.setLogoutTime(LocalDateTime.now());
        session.setActive(false);

        userSessionRepository.save(session);
    }

}