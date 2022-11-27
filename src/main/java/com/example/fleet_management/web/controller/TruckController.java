package com.example.fleet_management.web.controller;

import com.example.fleet_management.domain.service.TruckService;
import com.example.fleet_management.exception.truck.ExistingLicensePlateException;
import com.example.fleet_management.exception.ExistingRecordException;
import com.example.fleet_management.web.dto.error.ErrorResponseBody;
import com.example.fleet_management.web.dto.request.TruckRequestBody;
import com.example.fleet_management.web.dto.response.TruckResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.example.fleet_management.web.dto.response.TruckResponseBody.fromTruck;

@RestController
@RequestMapping("/truck")
public class TruckController {

    private final TruckService service;

    public TruckController(TruckService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {

        final var trucks = service.findAll()
                .stream()
                .map(TruckResponseBody::fromTruck)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(trucks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {

        final var truck = service
                .findById(id)
                .map(TruckResponseBody::fromTruck);

        return ResponseEntity.of(truck);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody TruckRequestBody body) {

        try {

            final var truck = service.save(body.toTruck());
            return ResponseEntity.ok(fromTruck(truck));
        }
        catch (ExistingRecordException | ExistingLicensePlateException e) {

            final var response = new ErrorResponseBody(e.getMessage());

            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody TruckRequestBody body) {

        final var truck = service
                .update(id, body.toTruck())
                .map(TruckResponseBody::fromTruck);

        return ResponseEntity.of(truck);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

       final var deleted = service.delete(id);

       if(!deleted) {
           return ResponseEntity.notFound().build();
       }

       return ResponseEntity.noContent().build();
    }
}
