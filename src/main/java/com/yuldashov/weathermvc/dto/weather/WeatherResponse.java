package com.yuldashov.weathermvc.dto.weather;

import java.math.BigDecimal;

public record WeatherResponse(String name, BigDecimal lat, BigDecimal lon, String main, double temp) {
}
