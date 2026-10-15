package com.yuldashov.weathermvc.dto.location;

public record LocationApiResponse(String name, double lat, double lon, String country) {
}
