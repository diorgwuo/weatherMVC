package com.yuldashov.weathermvc.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {
    private Coord coord;
    private Weather[] weather;
    private Main main;
    private String name;

    @Setter
    @Getter
    public static class Coord {
        private BigDecimal lon;
        private BigDecimal lat;
    }

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private String main;
    }

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        private double temp;
    }
}
