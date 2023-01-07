package com.example.fleet_management.web.controller;

import ch.qos.logback.core.net.AbstractSSLSocketAppender;
import com.example.fleet_management.domain.Location;
import com.example.fleet_management.domain.service.LocationService;
import com.example.fleet_management.domain.validator.LocationGeoCoordinateValidator;
import com.example.fleet_management.exception.ExistingRecordException;
import com.example.fleet_management.exception.location.InvalidCoordinateException;
import com.example.fleet_management.web.dto.error.ErrorResponseBody;
import com.example.fleet_management.web.dto.request.LocationRequestBody;
import com.example.fleet_management.web.dto.response.LocationResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class LocationControllerTest {


    @InjectMocks
    LocationController controller;
    @Mock
    LocationService service;

    @Mock
    LocationGeoCoordinateValidator coordinateValidator;

    private Location location;
    private Set<Location> locations;
    private Set<LocationResponseBody> locationsResponseBody;


    @BeforeEach
    void setUp() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        this.location = new Location(1L, "Foo Origin", BigDecimal.valueOf(1), BigDecimal.valueOf(2), Collections.emptySet(), Collections.emptySet());

        this.locations = new HashSet<>();
        this.locations.add(location);

        this.locationsResponseBody = this.locations.stream().map(LocationResponseBody::fromLocation).collect(Collectors.toSet());
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

        when(service.findAll()).thenReturn(locations);

        final var response = controller.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(locationsResponseBody, response.getBody());
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

        when(service.findById(anyLong())).thenReturn(Optional.of(location));

        final var response = controller.findById(1L);

        final var locationResponseBody = LocationResponseBody.fromLocation(location);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(locationResponseBody, response.getBody());
    }

    @Test
    void create_WhenIdAlreadyExists_Returns400() {

        doThrow(new ExistingRecordException("location", "ID")).when(service).save(any(Location.class));

        final var locationRequestBody = new LocationRequestBody(1L, "Foo RequestBody", BigDecimal.valueOf(1), BigDecimal.valueOf(2));

        final var response = controller.create(locationRequestBody);

        final var errorResponseBody = new ErrorResponseBody("A location with the provided ID already exists");

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(errorResponseBody, response.getBody());
    }

    @Test
    void create_WhenNameAlreadyExists_Returns400() {

        doThrow(new ExistingRecordException("location", "name")).when(service).save(any(Location.class));

        final var locationRequestBody = new LocationRequestBody(1L, "Foo RequestBody", BigDecimal.valueOf(1), BigDecimal.valueOf(2));

        final var response = controller.create(locationRequestBody);

        final var errorResponseBody = new ErrorResponseBody("A location with the provided name already exists");

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(errorResponseBody, response.getBody());
    }

    @Test
    void create_WhenLatitudeIsInvalid_Returns400() {

        final var locationRequestBody = new LocationRequestBody(1L, "Foo RequestBody", BigDecimal.valueOf(1), BigDecimal.valueOf(2));

        doThrow(new InvalidCoordinateException("Latitude", "1")).when(service).save(locationRequestBody.toLocation());

        final var response = controller.create(locationRequestBody);

        final var errorResponseBody = new ErrorResponseBody("Latitude '1' is invalid. Pattern must be DECIMAL{8,6}, e.g. '12.345678'");

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(errorResponseBody, response.getBody());
    }

    @Test
    void create_WhenLongitudeIsInvalid_Returns400() {

        final var locationRequestBody = new LocationRequestBody(1L, "Foo RequestBody", BigDecimal.valueOf(1), BigDecimal.valueOf(2));

        doThrow(new InvalidCoordinateException("Longitude", "2")).when(service).save(locationRequestBody.toLocation());

        final var response = controller.create(locationRequestBody);

        final var errorResponseBody = new ErrorResponseBody("Longitude '2' is invalid. Pattern must be DECIMAL{8,6}, e.g. '12.345678'");

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(errorResponseBody, response.getBody());
    }

    @Test
    void create_WhenDataIsValid_Returns201() {

        when(service.save(any(Location.class))).thenReturn(location);

        final var locationRequestBody = new LocationRequestBody(1L, "Foo RequestBody", BigDecimal.valueOf(1), BigDecimal.valueOf(2));

        final var response = controller.create(locationRequestBody);

        final var locationResponseBody = LocationResponseBody.fromLocation(location);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(locationResponseBody, response.getBody());
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
