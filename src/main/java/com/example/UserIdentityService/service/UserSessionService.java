package com.example.UserIdentityService.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.el.stream.Optional;
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

    	List<UserSession> existingSessions =
                userSessionRepository.findByUserUsernameAndActiveTrue(
                        user.getUsername());

        // Close any existing active session
        for (UserSession session : existingSessions) {

            session.setLogoutTime(LocalDateTime.now());
            session.setActive(false);
            session.setStatus("LOGGED_OUT");

            userSessionRepository.save(session);
        }
    	    
    	
    	
    	
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

        List<UserSession> activeSessions =
                userSessionRepository.findByUserUsernameAndActiveTrue(username);

        if (activeSessions.isEmpty()) {
            throw new RuntimeException("No active session found");
        }

        for (UserSession session : activeSessions) {

            session.setLogoutTime(LocalDateTime.now());
            session.setActive(false);
            session.setStatus("LOGGED_OUT");

            userSessionRepository.save(session);
        }
    }

}