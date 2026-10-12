package com.yuldashov.weathermvc.controller;

import com.yuldashov.weathermvc.model.User;
import com.yuldashov.weathermvc.service.AutorizationService;
import com.yuldashov.weathermvc.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AutorizationController {
    private final AutorizationService autorizationService;

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody User user) {
        autorizationService.register(user);
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody User user, HttpServletResponse response) {
        autorizationService.login(user.getLogin(), user.getPassword(), response);
    }
}
