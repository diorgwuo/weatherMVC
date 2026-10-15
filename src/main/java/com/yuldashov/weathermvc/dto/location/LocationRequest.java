package com.yuldashov.weathermvc.dto.location;


import java.math.BigDecimal;

public record LocationRequest(String name, BigDecimal lat, BigDecimal lon) {
}
