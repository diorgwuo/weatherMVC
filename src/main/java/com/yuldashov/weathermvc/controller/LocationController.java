package com.yuldashov.weathermvc.controller;

import com.yuldashov.weathermvc.dto.LocationRequest;
import com.yuldashov.weathermvc.dto.LocationResponse;
import com.yuldashov.weathermvc.exception.UnauthorizedException;
import com.yuldashov.weathermvc.model.User;
import com.yuldashov.weathermvc.service.LocationService;
import com.yuldashov.weathermvc.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;
    private final SessionService sessionService;

    @Autowired
    public LocationController(LocationService locationService, SessionService sessionService) {
        this.locationService = locationService;
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<?> saveLocation(
            @RequestBody LocationRequest request,
            @CookieValue(value = "sessionID") String sessionID) {
        if (sessionService.checkSession(sessionID)) {
            User user = sessionService.findBySessionID(sessionID);
            locationService.save(request, user);
            return ResponseEntity.ok().build();
        }
        throw new UnauthorizedException();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(
            @CookieValue(value = "sessionID") String sessionID,
            @PathVariable Integer id) {
        if (sessionService.checkSession(sessionID)) {
            locationService.delete(id);
            return ResponseEntity.ok().build();
        }
        throw new UnauthorizedException();
    }

    @GetMapping("/api/locations")
    public List<LocationResponse> showLocations(
            @CookieValue(value = "sessionID") String sessionID) {
        if (sessionService.checkSession(sessionID)) {
            return locationService.showLocations();
        }
        throw new UnauthorizedException();
    }
}
