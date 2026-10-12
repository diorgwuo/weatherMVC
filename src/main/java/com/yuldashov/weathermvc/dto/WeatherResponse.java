package com.yuldashov.weathermvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class WeatherResponse {
    private Coord coord;
    private Weather[] weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;

    @Setter
    @Getter
    public static class Coord {
        private BigDecimal lon;
        private BigDecimal lat;

    }

    @Setter
    @Getter
    public static class Weather {
        private int id;
        private String main;
        private String descripton;
        private String icon;

    }

    @Setter
    @Getter
    public static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;
        private int sea_level;
        private int grnd_level;

    }

    @Setter
    @Getter
    public static class Wind {
        private double speed;
        private int deg;
        private double gust;

    }

    @Setter
    @Getter
    public static class Rain {
        @JsonProperty("1h")
        private double oneH;

    }

    @Setter
    @Getter
    public static class Clouds {
        private int all;

    }

    @Setter
    @Getter
    public static class Sys {
        private int type;
        private int id;
        private String country;
        private long sunrise;
        private long sunset;

    }


}
