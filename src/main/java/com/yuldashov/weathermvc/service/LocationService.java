package com.yuldashov.weathermvc.service;

import com.yuldashov.weathermvc.dto.location.LocationRequest;
import com.yuldashov.weathermvc.dto.location.LocationResponse;
import com.yuldashov.weathermvc.dto.weather.WeatherResponse;
import com.yuldashov.weathermvc.exception.UnauthorizedException;
import com.yuldashov.weathermvc.model.Location;
import com.yuldashov.weathermvc.model.User;
import com.yuldashov.weathermvc.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final SessionService sessionService;
    private final WeatherService weatherService;

    @Transactional
    public void save(String sessionID, LocationRequest request) {
        if (!sessionService.checkSession(sessionID)) {
            throw new UnauthorizedException();
        }
        User user = sessionService.findBySessionID(sessionID);
        Location locationSave = new Location();
        locationSave.setName(request.name());
        locationSave.setLatitude(request.lat());
        locationSave.setLongitude(request.lon());
        locationSave.setUser(user);
        locationRepository.save(locationSave);
    }

    @Transactional
    public void delete(String sessionID, int id) {
        if (!sessionService.checkSession(sessionID)) {
            throw new UnauthorizedException();
        }
        locationRepository.deleteById(id);
    }

    public List<LocationResponse> showLocations(String sessionID) {
        if (!sessionService.checkSession(sessionID)) {
            throw new UnauthorizedException();
        }
        User user = sessionService.findBySessionID(sessionID);
        return locationRepository.findByUser(user).stream()
                .map(location -> {
                    BigDecimal lat = location.getLatitude();
                    BigDecimal lon = location.getLongitude();
                    int id = location.getId();
                    WeatherResponse weather = weatherService.getWeatherByCoord(sessionID, lat, lon);
                    double temp = weather.temp();
                    return new LocationResponse(location.getName(), temp, id);
                })
                .collect(Collectors.toList());
    }
}
