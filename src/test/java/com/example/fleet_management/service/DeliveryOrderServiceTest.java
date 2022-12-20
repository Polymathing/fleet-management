package com.example.fleet_management.service;

import com.example.fleet_management.dao.DeliveryOrderDAO;
import com.example.fleet_management.domain.DeliveryOrder;
import com.example.fleet_management.domain.Location;
import com.example.fleet_management.domain.Truck;
import com.example.fleet_management.domain.service.DeliveryOrderService;
import com.example.fleet_management.exception.EqualValuesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class DeliveryOrderServiceTest {

    private DeliveryOrderService service;
    @Mock
    private DeliveryOrderDAO dao;

    private Truck truck;
    private Location origin;
    private Location destination;
    private DeliveryOrder deliveryOrder;

    private Set<DeliveryOrder> deliveryOrders;

    @BeforeEach
    void setUp() {

        service = new DeliveryOrderService(this.dao);

        this.truck = new Truck(1L, "123", "Foo", "Foo", 1F, new HashSet<>());
        this.origin = new Location(1L, "Foo Origin", BigDecimal.valueOf(1), BigDecimal.valueOf(2), Collections.emptySet(), Collections.emptySet());
        this.destination = new Location(2L, "Foo Destination", BigDecimal.valueOf(2), BigDecimal.valueOf(3), Collections.emptySet(), Collections.emptySet());

        this.deliveryOrder = new DeliveryOrder(
                1L,
                truck,
                origin,
                destination,
                1.0,
                LocalDateTime.parse("2022-12-11T22:51:04.079665400")
        );

        deliveryOrders = new HashSet<>();
        deliveryOrders.add(deliveryOrder);
    }

    @Test
    void findAll_whenNoData_ReturnsEmptySet() {

        when(dao.findAll()).thenReturn(Collections.emptySet());

        final var response = service.findAll();

        assertEquals(Collections.emptySet(), response);
    }

    @Test
    void findAll_HavingDeliveryOrderSaved_ReturnsASetOfDeliveryOrders() {

        when(dao.findAll()).thenReturn(deliveryOrders);

        final var response = service.findAll();

        assertEquals(deliveryOrders, response);
    }

    @Test
    void findById_WhenIdDoesNotExist_ReturnsOptionalEmpty() {

        when(dao.findById(anyLong())).thenReturn(Optional.empty());

        final var response = service.findById(1L);

        assertEquals(Optional.empty(), response);
    }

    @Test
    void findById_WhenIdExists_ReturnsOptionalOfDeliveryOrder() {

        when(dao.findById(anyLong())).thenReturn(Optional.of(deliveryOrder));

        final var response = service.findById(1L);

        assertEquals(Optional.of(deliveryOrder), response);
    }

    @Test
    void save_WhenOriginAndDestinationAreEqual_ThrowsEqualValuesException() {

        assertThatExceptionOfType(EqualValuesException.class)
                .isThrownBy(() -> service.save(deliveryOrder.truck().licensePlate(), 1L, 1L))
                .withMessage("Origin and Destination cannot be the same.");
    }

    @Test
    void save_WhenValidData_SavesSuccessfully() {

        when(dao.save(anyString(), anyLong(), anyLong())).thenReturn(deliveryOrder);

        final var response = service.save("123", 1L, 2L);

        assertEquals(deliveryOrder, response);
    }

    @Test
    void delete_WhenIdDoesNotExist_ReturnsFalse() {

        when(dao.deleteById(anyLong())).thenReturn(false);

        final var response = service.delete(2L);

        assertFalse(response);
    }

    @Test
    void delete_WhenIdExists_ReturnsTrue() {

        when(dao.deleteById(anyLong())).thenReturn(true);

        final var response = service.delete(1L);

        assertTrue(response);
    }
}
