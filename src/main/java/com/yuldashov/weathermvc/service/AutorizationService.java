package com.yuldashov.weathermvc.service;

import com.yuldashov.weathermvc.dto.auth.LoginRequest;
import com.yuldashov.weathermvc.exception.NotFoundException;
import com.yuldashov.weathermvc.model.User;
import com.yuldashov.weathermvc.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static com.yuldashov.weathermvc.util.CoockieConstant.SESSION_COOKIE;

@Service
@RequiredArgsConstructor
public class AutorizationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;

    @Transactional
    public void register(LoginRequest loginRequest) {
        String hashPassword = passwordEncoder.encode(loginRequest.password());
        User userToSave = new User(loginRequest.login(), hashPassword);
        userRepository.save(userToSave);
    }

    @Transactional
    public void login(String login, String password, HttpServletResponse response) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким логином: " + login + " не найден" ));
        if (passwordEncoder.matches(password, user.getPassword())) {
            UUID id = sessionService.getOrCreate(Instant.now(), user);
            Cookie cookie = new Cookie(SESSION_COOKIE, id.toString());
            response.addCookie(cookie);
        } else {
            throw new RuntimeException("Неверный пароль");
        }
    }

}
