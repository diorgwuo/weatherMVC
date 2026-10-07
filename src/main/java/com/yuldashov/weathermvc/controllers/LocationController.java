package com.yuldashov.weathermvc.controllers;

import com.yuldashov.weathermvc.dto.LocationRequest;
import com.yuldashov.weathermvc.dto.LocationResponse;
import com.yuldashov.weathermvc.exception.UnauthorizedException;
import com.yuldashov.weathermvc.models.Location;
import com.yuldashov.weathermvc.models.User;
import com.yuldashov.weathermvc.service.LocationService;
import com.yuldashov.weathermvc.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class LocationController {
    private final LocationService locationService;
    private final SessionService sessionService;

    @Autowired
    public LocationController(LocationService locationService, SessionService sessionService) {
        this.locationService = locationService;
        this.sessionService = sessionService;
    }


    @PostMapping("/api/location")
    public ResponseEntity<?> saveLocation(
            @RequestBody LocationRequest request,
            @CookieValue(value = "sessionID", required = false) String sessionID) {
                if (sessionService.checkSession(sessionID)) {
                    User user  = sessionService.findBySessionID(sessionID);
                    locationService.save(request,user);
                    return ResponseEntity.ok().build();
                }
                throw new UnauthorizedException("Not authorized");
    }

    @DeleteMapping("/api/location/{id}")
    public ResponseEntity<?> deleteLocation(
            @CookieValue(value = "sessionID", required = false) String sessionID,
            @PathVariable Integer id) {
        if (sessionService.checkSession(sessionID)) {
            locationService.delete(id);
            return ResponseEntity.ok().build();
        }
        throw new UnauthorizedException("Not authorized");
    }

    @GetMapping("/api/locations")
    public List<LocationResponse> showLocation(
            @CookieValue(value = "sessionID", required = false) String sessionID) {
        if (sessionService.checkSession(sessionID)) {
            return locationService.showLocations();
        }
        throw new UnauthorizedException("Not authorized");
    }
}
