package com.yuldashov.weathermvc.controller;

import com.yuldashov.weathermvc.dto.location.LocationRequest;
import com.yuldashov.weathermvc.dto.location.LocationResponse;
import com.yuldashov.weathermvc.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yuldashov.weathermvc.util.CoockieConstant.SESSION_COOKIE;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void saveLocation(
            @RequestBody LocationRequest request,
            @CookieValue(value = SESSION_COOKIE) String sessionID) {
        locationService.save(sessionID, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLocation(
            @CookieValue(value = SESSION_COOKIE) String sessionID,
            @PathVariable Integer id) {
        locationService.delete(sessionID, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LocationResponse> showLocations(
            @CookieValue(value = SESSION_COOKIE) String sessionID) {
        return locationService.showLocations(sessionID);
    }
}
