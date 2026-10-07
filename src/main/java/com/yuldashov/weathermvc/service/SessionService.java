    package com.yuldashov.weathermvc.service;

    import com.yuldashov.weathermvc.dto.LocationRequest;
    import com.yuldashov.weathermvc.exception.NotFoundException;
    import com.yuldashov.weathermvc.models.Session;
    import com.yuldashov.weathermvc.models.User;
    import com.yuldashov.weathermvc.repository.SessionRepository;
    import com.yuldashov.weathermvc.repository.UserRepository;
    import com.yuldashov.weathermvc.security.PasswordCheck;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.time.LocalDateTime;
    import java.util.UUID;

    @Service
    public class SessionService {
        private final SessionRepository sessionRepository;
        private final UserRepository userRepository;
        private final PasswordCheck passwordCheck;

        @Autowired
        public SessionService(SessionRepository sessionRepository, UserRepository userRepository, PasswordCheck passwordCheck) {
            this.sessionRepository = sessionRepository;
            this.userRepository = userRepository;
            this.passwordCheck = passwordCheck;
        }

        @Transactional
        public UUID login(String login, String password) {
            LocalDateTime now = LocalDateTime.now();
            User user = userRepository.findByLogin(login);
            if (user == null) {
                throw new RuntimeException("User not found" + login);
            }
            if (passwordCheck.passwordVerification(user.getPassword(), password)) {
                if (sessionRepository.existByUserAndExpiresAt(user, now)) {
                    UUID id = sessionRepository.findSessionByUser(user,now).get().getId();
                    return id;
                }
                Session session = new Session();
                session.setExpiresAt(LocalDateTime.now().plusMinutes(60));
                session.setUser(user);
                sessionRepository.save(session);
                return session.getId();
            }
            throw new RuntimeException("Не подходит пароль");
        }

        public boolean checkSession(String sessionId) {
            if (sessionId == null) {
                return false;
            }
            LocalDateTime now = LocalDateTime.now();
            UUID id = UUID.fromString(sessionId);
            return sessionRepository.checkSessionById(id, now);
        }

        public User findBySessionID(String sessionID) {
            if (sessionID == null) {
                return null;
            }
            UUID id = UUID.fromString(sessionID);
            return sessionRepository.findUserBySession(id).orElse(null);
        }
}
