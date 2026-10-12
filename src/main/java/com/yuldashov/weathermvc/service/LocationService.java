package com.yuldashov.weathermvc.service;

import com.yuldashov.weathermvc.dto.*;
import com.yuldashov.weathermvc.model.Location;
import com.yuldashov.weathermvc.model.User;
import com.yuldashov.weathermvc.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final WeatherService weatherService;

    public void save(LocationRequest request, User user) {
        Location locationSave = new Location();
        locationSave.setName(request.name());
        locationSave.setLatitude(request.lat());
        locationSave.setLongitude(request.lon());
        locationSave.setUser(user);
        locationRepository.save(locationSave);
    }

    public void delete(int id) {
        locationRepository.deleteById(id);
    }

    public List<LocationResponse> showLocations() {
        return locationRepository.findAll().stream()
                .map(location -> {
                    BigDecimal lat = location.getLatitude();
                    BigDecimal lon = location.getLongitude();
                    WeatherDTO weather = weatherService.getWeatherByCoord(lat,lon);
                    double temp = weather.temp();
                    return new LocationResponse(location.getName(), temp);
                })
                .collect(Collectors.toList());
    }

}
