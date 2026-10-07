package com.yuldashov.weathermvc.service;

import com.yuldashov.weathermvc.dto.*;
import com.yuldashov.weathermvc.models.Location;
import com.yuldashov.weathermvc.models.User;
import com.yuldashov.weathermvc.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final SessionService sessionService;
    private final WeatherService weatherService;

    @Autowired
    public LocationService(LocationRepository locationRepository, SessionService sessionService, WeatherService weatherService) {
        this.locationRepository = locationRepository;
        this.sessionService = sessionService;
        this.weatherService = weatherService;
    }

    public void save(LocationRequest request, User user) {
        Location locationSave = new Location();
        locationSave.setName(request.getName());
        locationSave.setLatitude(request.getLat());
        locationSave.setLongitude(request.getLon());
        locationSave.setUser(user);
        locationRepository.save(locationSave);
    }

    public void delete(int id) {
        locationRepository.deleteById(id);
    }

    public List<LocationResponse> showLocations() {
        List<LocationResponse> responses = new ArrayList<>();
        List<Location> locations = locationRepository.findAll();
        for (Location location : locations) {
            LocationResponse response = new LocationResponse();
            response.setName(location.getName());
            BigDecimal lat = location.getLatitude();
            BigDecimal lon = location.getLongitude();
            WeatherDTO weather = weatherService.getWeatherByCoord(lat,lon);
            double temp = weather.getTemp();
            response.setTemp(temp);
            responses.add(response);
        }

        return responses;
    }

}
