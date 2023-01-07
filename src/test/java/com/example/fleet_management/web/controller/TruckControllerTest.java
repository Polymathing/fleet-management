package com.example.fleet_management.web.controller;

import com.example.fleet_management.domain.Truck;
import com.example.fleet_management.domain.service.TruckService;
import com.example.fleet_management.exception.ExistingRecordException;
import com.example.fleet_management.web.dto.error.ErrorResponseBody;
import com.example.fleet_management.web.dto.request.TruckRequestBody;
import com.example.fleet_management.web.dto.response.TruckResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TruckControllerTest {

    @InjectMocks
    TruckController controller;

    @Mock
    TruckService service;

    private Truck truck;

    private Set<Truck> trucks;

    private Set<TruckResponseBody> trucksResponseBody;

    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        this.truck = new Truck(1L, "123", "Foo", "Foo", 1F, new HashSet<>());

        this.trucks = new HashSet<>();
        this.trucks.add(truck);

        this.trucksResponseBody = this.trucks.stream().map(TruckResponseBody::fromTruck).collect(Collectors.toSet());
    }

    @Test
    void find_AllWhenDataIsNotFound_Returns200EmptySet() {

        when(service.findAll()).thenReturn(Collections.emptySet());

        final var response = controller.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Collections.emptySet(), response.getBody());
    }

    @Test
    void findAll_WhenDataIsFound_Returns200WithData() {

        when(service.findAll()).thenReturn(trucks);

        final var response = controller.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(trucksResponseBody, response.getBody());
    }

    @Test
    void findById_WhenNoDataIsFound_Returns404() {

        when(service.findById(anyLong())).thenReturn(Optional.empty());

        final var response = controller.findById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void findById_WhenDataIsFound_Returns200WithBody() {

        when(service.findById(anyLong())).thenReturn(Optional.of(truck));

        final var response = controller.findById(1L);

        final var truckResponseBody = TruckResponseBody.fromTruck(truck);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(truckResponseBody, response.getBody());
    }

    @Test
    void create_WhenIdAlreadyExists_Returns400() {

        doThrow(new ExistingRecordException("truck", "ID")).when(service).save(any(Truck.class));

        final var truckRequestBody = new TruckRequestBody(1L, "123", "Foo", "Foo", 1F);

        final var response = controller.create(truckRequestBody);

        final var errorResponseBody = new ErrorResponseBody("A truck with the provided ID already exists");

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(errorResponseBody, response.getBody());
    }

    @Test
    void create_WhenLicensePlateAlreadyExists_Returns400() {

        doThrow(new ExistingRecordException("truck", "License Plate")).when(service).save(any(Truck.class));

        final var truckRequestBody = new TruckRequestBody(1L, "123", "Foo", "Foo", 1F);

        final var response = controller.create(truckRequestBody);

        final var errorResponseBody = new ErrorResponseBody("A truck with the provided License Plate already exists");

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(errorResponseBody, response.getBody());
    }

    @Test
    void create_WhenDataIsValid_Returns201() {

        when(service.save(any(Truck.class))).thenReturn(truck);

        final var truckRequestBody = new TruckRequestBody(1L, "123", "Foo", "Foo", 1F);

        final var response = controller.create(truckRequestBody);

        final var truckResponseBody = TruckResponseBody.fromTruck(truck);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(truckResponseBody, response.getBody());
    }

    @Test
    void update_WhenIdDoesNotExist_Returns404() {

        when(service.update(anyLong(), any(Truck.class))).thenReturn(Optional.empty());

        final var updatedTruck = new TruckRequestBody(1L, "123", "Foo", "Foo", 1F);

        final var response = controller.update(1L, updatedTruck);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void update_WhenDataIsValid_Returns200() {

        when(service.update(anyLong(), any(Truck.class))).thenReturn(Optional.of(truck));

        final var updatedTruck = new TruckRequestBody(1L, "123", "Foo", "Foo", 1F);

        final var response = controller.update(1L, updatedTruck);

        final var truckResponseBody = TruckResponseBody.fromTruck(truck);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(truckResponseBody, response.getBody());
    }

    @Test
    void delete_WhenNoDataIsFound_Returns404NotFound() {

        when(service.delete(anyLong())).thenReturn(false);

        final var response = controller.delete(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void delete_WhenDataIsFound_Returns204NoContent() {

        when(service.delete(anyLong())).thenReturn(true);

        final var response = controller.delete(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

}
