package com.example.fleet_management.web.controller;

import com.example.fleet_management.domain.service.LocationService;
import com.example.fleet_management.exception.ExistingRecordException;
import com.example.fleet_management.exception.location.InvalidCoordinateException;
import com.example.fleet_management.web.dto.error.ErrorResponseBody;
import com.example.fleet_management.web.dto.request.LocationRequestBody;
import com.example.fleet_management.web.dto.response.LocationResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.example.fleet_management.web.dto.response.LocationResponseBody.fromLocation;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {

        final var locations = service.findAll()
                .stream()
                .map(LocationResponseBody::fromLocation)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {

        final var location = service.findById(id)
                .map(LocationResponseBody::fromLocation);

        return ResponseEntity.of(location);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LocationRequestBody body) {

        try {

            final var location = service.save(body.toLocation());

            return ResponseEntity
                    .status(201)
                    .body(fromLocation(location));
        }
        catch (ExistingRecordException | InvalidCoordinateException e) {

            final var error = new ErrorResponseBody(e.getMessage());

            return ResponseEntity.badRequest()
                    .body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        final var deleted = service.delete(id);

        if(!deleted) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .noContent()
                .build();
    }
}
