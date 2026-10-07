package com.yuldashov.weathermvc.controllers;

import com.yuldashov.weathermvc.dto.AutorizationRequest;
import com.yuldashov.weathermvc.models.Session;
import com.yuldashov.weathermvc.models.User;
import com.yuldashov.weathermvc.service.AutorizationService;
import com.yuldashov.weathermvc.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.SeekableByteChannel;
import java.util.UUID;

@RestController
public class AutorizationController {
    private final AutorizationService autorizationService;
    private final SessionService sessionService;

    @Autowired
    public AutorizationController(AutorizationService autorizationService, SessionService sessionService) {
        this.autorizationService = autorizationService;
        this.sessionService = sessionService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        autorizationService.register(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        UUID id = sessionService.login(user.getLogin(), user.getPassword());
        Cookie cookie = new Cookie("sessionID",id.toString());
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
