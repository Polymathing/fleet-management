package com.example.fleet_management.dao;

import com.example.fleet_management.dao.entity.DeliveryOrderRow;
import com.example.fleet_management.dao.entity.LocationRow;
import com.example.fleet_management.dao.entity.TruckRow;
import com.example.fleet_management.dao.repository.DeliveryOrderRepository;
import com.example.fleet_management.dao.repository.LocationRepository;
import com.example.fleet_management.dao.repository.TruckRepository;
import com.example.fleet_management.domain.DeliveryOrder;
import com.example.fleet_management.exception.RecordNotFoundException;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class DeliveryOrderDAOTest {

    private DeliveryOrderDAO dao;

    @Mock
    private DeliveryOrderRepository repository;
    @Mock
    private LocationRepository locationRepository;

    @Mock
    private TruckRepository truckRepository;

    private Set<DeliveryOrder> deliveryOrders;
    private DeliveryOrder deliveryOrder;

    private DeliveryOrderRow deliveryOrderRow;

    private TruckRow truckRow;
    private LocationRow originRow;
    private LocationRow destinationRow;

    @BeforeEach
    void setUp() {

        this.truckRow = new TruckRow(1L, "123", "Foo", "Foo", 1F, new HashSet<>());
        this.originRow = new LocationRow(1L, "Foo Origin", BigDecimal.valueOf(1), BigDecimal.valueOf(2), Collections.emptySet(), Collections.emptySet());
        this.destinationRow = new LocationRow(2L, "Foo Destination", BigDecimal.valueOf(2), BigDecimal.valueOf(3), Collections.emptySet(), Collections.emptySet());

        this.dao = new DeliveryOrderDAO(repository, locationRepository, truckRepository);

        this.deliveryOrderRow = new DeliveryOrderRow(
                1L,
                truckRow,
                originRow,
                destinationRow,
                1.0,
                LocalDateTime.parse("2022-12-11T22:51:04.079665400")
        );

        this.deliveryOrder = new DeliveryOrder(
                1L,
                truckRow.toTruck(),
                originRow.toLocation(),
                destinationRow.toLocation(),
                1.0,
                LocalDateTime.parse("2022-12-11T22:51:04.079665400")
        );

        this.deliveryOrders = new HashSet<>();
        this.deliveryOrders.add(deliveryOrder);
    }

    @Test
    void findAll_NotHavingDeliveryOrdersSaved_ReturnsEmptySet() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        final var response = dao.findAll();

        assertEquals(Collections.emptySet(), response);
    }

    @Test
    void findAll_HavingDeliveryOrdersSaved_ReturnsAllLocationsSaved() {

        when(repository.findAll()).thenReturn(List.of(deliveryOrderRow));

        final var response = dao.findAll();

        assertEquals(deliveryOrders, response);
    }

    @Test
    void findById_WhenDeliveryOrderNotFound_ReturnsOptionalEmpty() {

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        final var response = dao.findById(1L);

        assertEquals(Optional.empty(), response);
    }

    @Test
    void findById_WhenDeliveryOderIsFound_ReturnsOptionalLocation() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(deliveryOrderRow));

        final var response = dao.findById(1L);

        assertEquals(Optional.of(deliveryOrder), response);
    }

    @Test
    void save_WhenValidLocation_ReturnsSavedLocation() {

        when(repository.save(any(DeliveryOrderRow.class))).thenReturn(deliveryOrderRow);
        when(truckRepository.findByLicensePlate(anyString())).thenReturn(Optional.of(truckRow));
        when(locationRepository.findById(1L)).thenReturn(Optional.of(originRow));
        when(locationRepository.findById(2L)).thenReturn(Optional.of(destinationRow));

        final var response = dao.save("123", 1L, 2L);

        assertEquals(deliveryOrder, response);
    }

    @Test
    void save_WhenLicensePlateNotFound_ThrowsRecordNotFoundException() {

        when(truckRepository.findByLicensePlate(anyString())).thenReturn(Optional.empty());

        when(repository.save(any(DeliveryOrderRow.class))).thenReturn(deliveryOrderRow);
        when(locationRepository.findById(1L)).thenReturn(Optional.of(originRow));
        when(locationRepository.findById(2L)).thenReturn(Optional.of(destinationRow));

        Assertions.assertThatExceptionOfType(RecordNotFoundException.class)
                .isThrownBy(() -> dao.save("123", 1L, 2L))
                .withMessage("A truck with the provided license plate was not found");
    }

    @Test
    void save_WhenOriginLocationNotFound_ThrowsRecordNotFoundException() {

        when(truckRepository.findByLicensePlate(anyString())).thenReturn(Optional.of(truckRow));

        when(repository.save(any(DeliveryOrderRow.class))).thenReturn(deliveryOrderRow);
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());
        when(locationRepository.findById(2L)).thenReturn(Optional.of(destinationRow));

        Assertions.assertThatExceptionOfType(RecordNotFoundException.class)
                .isThrownBy(() -> dao.save("123", 1L, 2L))
                .withMessage("A origin with the provided ID was not found");
    }

    @Test
    void save_WhenDestinationLocationNotFound_ThrowsRecordNotFoundException() {

        when(truckRepository.findByLicensePlate(anyString())).thenReturn(Optional.of(truckRow));

        when(repository.save(any(DeliveryOrderRow.class))).thenReturn(deliveryOrderRow);
        when(locationRepository.findById(1L)).thenReturn(Optional.of(originRow));
        when(locationRepository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(RecordNotFoundException.class)
                .isThrownBy(() -> dao.save("123", 1L, 2L))
                .withMessage("A destination with the provided ID was not found");
    }

    @Test
    void delete_WhenDeleted_ShouldReturnTrue() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(deliveryOrderRow));

        final var response = dao.deleteById(1L);

        assertTrue(response);
    }

    @Test
    void delete_WhenNotDeleted_ShouldReturnFalse() {

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        final var response = dao.deleteById(1L);

        assertFalse(response);
    }

    @Test
    void calculateDistanceInKilometers_WhenLongitudeAndLatitudeEquals_ReturnsZero() {

        final var response = DeliveryOrderDAO.calculateDistanceInKilometers(
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1));

        assertEquals(BigDecimal.valueOf(0), response);
    }
}
