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

    @GetMapping("/api/weather")
    @ResponseStatus(HttpStatus.OK)
    public WeatherDTO getWeatherByCoord(
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lon,
            @CookieValue(value = "sessionID", required = false) String sessionID) {
        return weatherService.getWeatherByCoord(sessionID, lat, lon);
    }

    @GetMapping("/api/weather/search")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationDTO> getWeatherByName(
            @CookieValue(value = "sessionID", required = false) String sessionID,
            @RequestParam String q) {
        return weatherService.getWeatherByName(sessionID, q);
    }
}
