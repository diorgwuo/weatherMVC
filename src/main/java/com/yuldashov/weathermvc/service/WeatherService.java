package com.yuldashov.weathermvc.service;

import com.yuldashov.weathermvc.dto.LocationDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;
import com.yuldashov.weathermvc.dto.WeatherDTO;
import com.yuldashov.weathermvc.dto.WeatherResponse;
import com.yuldashov.weathermvc.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Service
public class WeatherService {
    private final WebClient weatherWebClient;
    private final WebClient geoWebClient;
    private final String apiKey = "95372ef7679b115bab75e92ac2c6d8d8";

    @Autowired
    public WeatherService(@Qualifier("weatherWebClient") WebClient weatherWebClient,
                          @Qualifier("geoWebClient") WebClient geoWebClient) {
        this.weatherWebClient = weatherWebClient;
        this.geoWebClient = geoWebClient;
    }


    public WeatherDTO getWeatherByCoord(BigDecimal lat, BigDecimal lon) {
        WeatherResponse response = weatherWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("lat", lat)
                        .queryParam("lon",lon)
                        .queryParam("appid",apiKey)
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .block();

        if (response!=null) {
            WeatherDTO weather = new WeatherDTO();
            weather.setLat(response.getCoord().getLat());
            weather.setLon(response.getCoord().getLon());
            weather.setName(response.getName());
            weather.setTemp(response.getMain().getTemp());
            weather.setMain(response.getWeather()[0].getMain());
            return weather;
        }
        throw new NotFoundException("Not found place with coord");
    }

    public List<LocationDTO> getWeatherByName(String name) {
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

        if (response!=null) {
            return Arrays.asList(response);
        }
        throw new NotFoundException("Not found place by name");
    }
}
