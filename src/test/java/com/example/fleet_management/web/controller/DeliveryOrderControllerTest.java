package com.example.fleet_management.web.controller;

import com.example.fleet_management.domain.DeliveryOrder;
import com.example.fleet_management.domain.Location;
import com.example.fleet_management.domain.Truck;
import com.example.fleet_management.domain.service.DeliveryOrderService;
import com.example.fleet_management.exception.EqualValuesException;
import com.example.fleet_management.exception.RecordNotFoundException;
import com.example.fleet_management.web.dto.error.ErrorResponseBody;
import com.example.fleet_management.web.dto.response.DeliveryOrderResponseBody;
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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class DeliveryOrderControllerTest {

    @InjectMocks
    DeliveryOrderController controller;

    @Mock
    DeliveryOrderService service;

    private Truck truck;
    private Location origin;
    private Location destination;
    private DeliveryOrder deliveryOrder;
    private Set<DeliveryOrder> deliveryOrders;
    private Set<DeliveryOrderResponseBody> deliveryOrderResponseBodySet;

    @BeforeEach
    void setUp() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        this.truck = new Truck(1L, "123", "Foo", "Foo", 1F, new HashSet<>());
        this.origin = new Location(1L, "Foo Origin", BigDecimal.valueOf(1), BigDecimal.valueOf(2), Collections.emptySet(), Collections.emptySet());
        this.destination = new Location(2L, "Foo Destination", BigDecimal.valueOf(2), BigDecimal.valueOf(3), Collections.emptySet(), Collections.emptySet());

        this.deliveryOrder = new DeliveryOrder(
                1L,
                truck,
                origin,
                destination,
                1.0,
                LocalDateTime.parse("2022-12-21T22:51:04.079665400")
        );

        this.deliveryOrders = new HashSet<>();
        this.deliveryOrders.add(deliveryOrder);

        this.deliveryOrderResponseBodySet = deliveryOrders.stream().map(DeliveryOrderResponseBody::fromDeliveryOrder)
                .collect(Collectors.toSet());
    }

    @Test
    void findAll_WhenNoDataIsNotFound_Returns200EmptySet() {

        when(service.findAll()).thenReturn(Collections.emptySet());

        final var response = controller.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Collections.emptySet(), response.getBody());
    }

    @Test
    void findAll_WhenDataIsFound_Returns200SetWithData() {

        when(service.findAll()).thenReturn(deliveryOrders);

        final var response = controller.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(deliveryOrderResponseBodySet, response.getBody());
    }

    @Test
    void findById_WhenIdDoesNotExist_Returns404() {

        when(service.findById(anyLong())).thenReturn(Optional.empty());

        final var response = controller.findById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void findById_WhenIdDoesExist_Returns200WithBody() {

        when(service.findById(anyLong())).thenReturn(Optional.of(deliveryOrder));

        final var response = controller.findById(1L);

        final var deliveryOrderResponseBody = DeliveryOrderResponseBody.fromDeliveryOrder(deliveryOrder);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(deliveryOrderResponseBody, response.getBody());
    }

    @Test
    void create_WhenAttributeIsNotFound_Returns404RecordNotFoundException() {

        doThrow(new RecordNotFoundException("truck", "license plate")).when(service).save(anyString(), anyLong(), anyLong());

        final var response = controller.create("123ABC", 1L, 1L);

        final var errorResponseBody = new ErrorResponseBody("A truck with the provided license plate was not found");

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(errorResponseBody, response.getBody());
    }

    @Test
    void create_WhenCoordinatesAreEqual_Returns400BadRequest() {

        doThrow(new EqualValuesException("Origin", "Destination")).when(service).save(anyString(), anyLong(), anyLong());

        final var response = controller.create("123ABC", 1L, 1L);

        final var errorResponseBody = new ErrorResponseBody("Origin and Destination cannot be the same");

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(errorResponseBody, response.getBody());
    }

    @Test
    void create_WhenValidData_Returns201Created() {

        when(service.save(anyString(), anyLong(), anyLong())).thenReturn(deliveryOrder);

        final var response = controller.create("123ABC", 1L, 1L);

        final var deliveryOrderResponseBody = DeliveryOrderResponseBody.fromDeliveryOrder(deliveryOrder);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(deliveryOrderResponseBody, response.getBody());
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