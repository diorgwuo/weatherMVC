package com.yuldashov.weathermvc.controller;

import com.yuldashov.weathermvc.dto.LocationRequest;
import com.yuldashov.weathermvc.dto.LocationResponse;
import com.yuldashov.weathermvc.exception.UnauthorizedException;
import com.yuldashov.weathermvc.model.User;
import com.yuldashov.weathermvc.service.LocationService;
import com.yuldashov.weathermvc.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void saveLocation(
            @RequestBody LocationRequest request,
            @CookieValue(value = "sessionID") String sessionID) {
        locationService.save(sessionID, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLocation(
            @CookieValue(value = "sessionID") String sessionID,
            @PathVariable Integer id) {
        locationService.delete(sessionID, id);
    }

    @GetMapping("/api/locations")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationResponse> showLocations(
            @CookieValue(value = "sessionID") String sessionID) {
        return locationService.showLocations(sessionID);
    }
}
