package com.example.fleet_management.web.controller;

import com.example.fleet_management.domain.service.DeliveryOrderService;
import com.example.fleet_management.exception.RecordNotFoundException;
import com.example.fleet_management.exception.EqualValuesException;
import com.example.fleet_management.web.dto.error.ErrorResponseBody;
import com.example.fleet_management.web.dto.response.DeliveryOrderResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.example.fleet_management.web.dto.response.DeliveryOrderResponseBody.fromDeliveryOrder;

@RestController
@RequestMapping("/deliveryOrder")
public class DeliveryOrderController {

    private final DeliveryOrderService service;

    public DeliveryOrderController(DeliveryOrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {

        final var deliveryOrders = service.findAll()
                .stream()
                .map(DeliveryOrderResponseBody::fromDeliveryOrder)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(deliveryOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {

        final var deliveryOrder = service.findById(id)
                .map(DeliveryOrderResponseBody::fromDeliveryOrder);

        return ResponseEntity.of(deliveryOrder);
    }

    @PostMapping("/truck/{licensePlate}/origin/{originId}/destination/{destinationId}")
    public ResponseEntity<?> create(@PathVariable("licensePlate") String licensePlate,
                                    @PathVariable("originId") Long originId,
                                    @PathVariable("destinationId") Long destinationId) {

        try {

            final var deliveryOrder = service.save(licensePlate, originId, destinationId);

            return ResponseEntity.ok(fromDeliveryOrder(deliveryOrder));
        }
        catch (RecordNotFoundException e) {

            final var error = new ErrorResponseBody(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(error);
        }
        catch (EqualValuesException e) {

            final var error = new ErrorResponseBody(e.getMessage());
            return ResponseEntity.badRequest().body(error);
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

        return ResponseEntity.noContent().build();
    }


}
