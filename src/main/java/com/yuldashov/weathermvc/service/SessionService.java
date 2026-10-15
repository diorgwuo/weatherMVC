package com.yuldashov.weathermvc.service;

import com.yuldashov.weathermvc.model.Session;
import com.yuldashov.weathermvc.model.User;
import com.yuldashov.weathermvc.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private static final Duration SESSION_TTL = Duration.ofMinutes(60);
    private final SessionRepository sessionRepository;

    @Transactional
    public UUID getOrCreate(Instant now, User user) {
        if (sessionRepository.existsByUserAndExpiresAtAfter(user, now)) {
            return sessionRepository.findSessionByUserAndExpiresAt(user, now).get().getId();
        } else {
            Session session = new Session();
            session.setExpiresAt(now.plus(SESSION_TTL));
            session.setUser(user);
            return sessionRepository.save(session).getId();
        }
    }

    @Transactional
    public boolean checkSession(String sessionId) {
        Instant now = Instant.now();
        UUID id = UUID.fromString(sessionId);
        return sessionRepository.findById(id)
                .filter(s -> s.getExpiresAt().isAfter(now))
                .map(s -> {
                    s.setExpiresAt(now.plus(SESSION_TTL));
                    return true;
                        })
                .orElse(false);
    }

    public User findBySessionID(String sessionID) {
        if (sessionID == null) {
            return null;
        }
        UUID id = UUID.fromString(sessionID);
        return sessionRepository.findUserBySession(id).orElse(null);
    }
}
