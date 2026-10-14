package com.yuldashov.weathermvc.service;

import com.yuldashov.weathermvc.dto.LocationDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;

import com.yuldashov.weathermvc.dto.WeatherDTO;
import com.yuldashov.weathermvc.dto.WeatherResponse;
import com.yuldashov.weathermvc.exception.NotFoundException;
import com.yuldashov.weathermvc.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WebClient weatherWebClient;
    private final WebClient geoWebClient;
    private final SessionService sessionService;
    private final String apiKey = "${api.key}";

    public WeatherDTO getWeatherByCoord(String sessionID, BigDecimal lat, BigDecimal lon) {
        if (sessionService.checkSession(sessionID)) {
            WeatherResponse response = weatherWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/weather")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("appid", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(WeatherResponse.class)
                    .block();

            if (response != null) {
                return new WeatherDTO(response.getName(),
                        response.getCoord().getLat(),
                        response.getCoord().getLon(),
                        response.getWeather()[0].getMain(),
                        response.getMain().getTemp()
                );
            }
            throw new NotFoundException("Not found place with coord");
        } else {
            throw new UnauthorizedException();
        }
    }

    public List<LocationDTO> getWeatherByName(String sessionID, String name) {
        if (sessionService.checkSession(sessionID)) {
            LocationDTO[] response = geoWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/direct")
                            .queryParam("q", name)
                            .queryParam("appid", apiKey)
                            .queryParam("limit", 5)
                            .build())
                    .retrieve()
                    .bodyToMono(LocationDTO[].class)
                    .block();
            if (response != null) {
                return Arrays.asList(response);
            }
            throw new NotFoundException("Not found place by name");
        } else {
            throw new UnauthorizedException();
        }
    }
}
