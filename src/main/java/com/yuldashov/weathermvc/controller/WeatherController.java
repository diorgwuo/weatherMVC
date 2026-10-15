package com.yuldashov.weathermvc.controller;

import com.yuldashov.weathermvc.dto.location.LocationApiResponse;
import com.yuldashov.weathermvc.dto.weather.WeatherResponse;

import java.math.BigDecimal;
import java.util.List;

import com.yuldashov.weathermvc.service.WeatherService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.yuldashov.weathermvc.util.CoockieConstant.SESSION_COOKIE;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public WeatherResponse getWeatherByCoord(
            @RequestParam @NotNull BigDecimal lat,
            @RequestParam @NotNull BigDecimal lon,
            @CookieValue(value = SESSION_COOKIE, required = false) String sessionID) {
        return weatherService.getWeatherByCoord(sessionID, lat, lon);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationApiResponse> getWeatherByName(
            @CookieValue(value = SESSION_COOKIE, required = false) String sessionID,
            @RequestParam String q) {
        return weatherService.getWeatherByName(sessionID, q);
    }
}
