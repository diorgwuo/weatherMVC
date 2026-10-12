package com.yuldashov.weathermvc.service;

import com.yuldashov.weathermvc.exception.NotFoundException;
import com.yuldashov.weathermvc.model.User;
import com.yuldashov.weathermvc.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorizationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;

    public User register(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        String hashPassword = passwordEncoder.encode(password);
        User userToSave = new User(login, hashPassword);
        return userRepository.save(userToSave);
    }

    @Transactional
    public void login(String login, String password, HttpServletResponse response) {
        Instant now = Instant.now();
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Такой пользователь не найден"));
        UUID id;
        if (user == null) {
            throw new RuntimeException("User not found" + login);
        }
        if (passwordEncoder.matches(user.getPassword(), password)) {
            id = sessionService.create(now, user);
            Cookie cookie = new Cookie("sessionID", id.toString());
            response.addCookie(cookie);
        } else {
            throw new RuntimeException("Не подходит пароль");
        }
    }

}
