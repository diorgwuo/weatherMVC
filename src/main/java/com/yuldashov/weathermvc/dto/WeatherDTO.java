package com.yuldashov.weathermvc.dto;

import java.math.BigDecimal;

public record WeatherDTO(String name, BigDecimal lat, BigDecimal lon, String main, double temp) {
}
