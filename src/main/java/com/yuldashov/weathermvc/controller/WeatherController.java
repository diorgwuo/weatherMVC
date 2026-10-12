package com.yuldashov.weathermvc.controller;

import com.yuldashov.weathermvc.dto.LocationDTO;
import com.yuldashov.weathermvc.dto.WeatherDTO;

import java.math.BigDecimal;
import java.util.List;

import com.yuldashov.weathermvc.exception.UnauthorizedException;
import com.yuldashov.weathermvc.service.SessionService;
import com.yuldashov.weathermvc.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    private final SessionService sessionService;

    @GetMapping("/api/weather")
    public ResponseEntity<?> getWeatherByCoord (
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lon,
            @CookieValue(value = "sessionID", required = false) String sessionID) {

        if (sessionService.checkSession(sessionID)) {
            WeatherDTO response = weatherService.getWeatherByCoord(lat, lon);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        throw new UnauthorizedException();
    }

    @GetMapping("/api/weather/search")
    public ResponseEntity<?> getWeatherByName (
            @CookieValue(value = "sessionID", required = false) String sessionID,
            @RequestParam String q) {

        if (sessionService.checkSession(sessionID)) {
            List<LocationDTO> response = weatherService.getWeatherByName(q);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        throw new UnauthorizedException();
    }
}
