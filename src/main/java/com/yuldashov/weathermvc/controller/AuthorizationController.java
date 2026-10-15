package com.yuldashov.weathermvc.controller;

import com.yuldashov.weathermvc.dto.auth.LoginRequest;
import com.yuldashov.weathermvc.service.AutorizationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {
    private final AutorizationService autorizationService;

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody LoginRequest loginRequest) {
        autorizationService.register(loginRequest);
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        autorizationService.login(loginRequest.login(), loginRequest.password(), response);
    }
}
