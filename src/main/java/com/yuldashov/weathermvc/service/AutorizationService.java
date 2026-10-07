package com.yuldashov.weathermvc.service;

import com.yuldashov.weathermvc.models.Session;
import com.yuldashov.weathermvc.models.User;
import com.yuldashov.weathermvc.repository.SessionRepository;
import com.yuldashov.weathermvc.repository.UserRepository;
import com.yuldashov.weathermvc.security.PasswordCheck;
import com.yuldashov.weathermvc.security.PasswordHashHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AutorizationService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final PasswordHashHandler passwordHashHandler;
    private final PasswordCheck passwordCheck;

    @Autowired
    public AutorizationService(SessionRepository sessionRepository, UserRepository userRepository,
                               PasswordHashHandler passwordHashHandler, PasswordCheck passwordCheck) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.passwordCheck = passwordCheck;
        this.passwordHashHandler = passwordHashHandler;
    }

    public User register(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        String hashPassword = passwordHashHandler.hashPassword(password);

        User userToSave = new User();
        userToSave.setLogin(login);
        userToSave.setPassword(hashPassword);
        return userRepository.save(userToSave);
    }

}
