package com.yuldashov.weathermvc.service;

import com.yuldashov.weathermvc.dto.location.LocationApiResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;

import com.yuldashov.weathermvc.dto.weather.WeatherResponse;
import com.yuldashov.weathermvc.dto.weather.WeatherApiResponse;
import com.yuldashov.weathermvc.exception.NotFoundException;
import com.yuldashov.weathermvc.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WebClient weatherWebClient;
    private final WebClient geoWebClient;
    private final SessionService sessionService;
    @Value("${api.key}")
    private String apiKey;

    public WeatherResponse getWeatherByCoord(String sessionID, BigDecimal lat, BigDecimal lon) {
        if (!sessionService.checkSession(sessionID)) {
            throw new UnauthorizedException();
        }
        WeatherApiResponse response = weatherWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block();

        if (response != null) {
            return new WeatherResponse(response.getName(),
                    response.getCoord().getLat(),
                    response.getCoord().getLon(),
                    response.getWeather()[0].getMain(),
                    response.getMain().getTemp()
            );
        }
        throw new NotFoundException("Не нашли город с такими координатами: lat - " + lat + " lon - " + lon);
    }

    public List<LocationApiResponse> getWeatherByName(String sessionID, String name) {
        if (!sessionService.checkSession(sessionID)) {
            throw new UnauthorizedException();
        }
        LocationApiResponse[] response = geoWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/direct")
                        .queryParam("q", name)
                        .queryParam("appid", apiKey)
                        .queryParam("limit", 5)
                        .build())
                .retrieve()
                .bodyToMono(LocationApiResponse[].class)
                .block();
        if (response != null) {
            return Arrays.asList(response);
        }
        throw new NotFoundException("Не нашли город с таким именем: " + name);
    }
}
