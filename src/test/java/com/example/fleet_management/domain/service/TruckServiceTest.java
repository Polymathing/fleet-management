package com.example.fleet_management.domain.service;


import com.example.fleet_management.dao.TruckDAO;
import com.example.fleet_management.domain.Truck;
import com.example.fleet_management.domain.service.TruckService;
import com.example.fleet_management.exception.ExistingRecordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TruckServiceTest {

    private TruckService service;

    @Mock
    private TruckDAO dao;

    private Truck truck;
    private Set<Truck> trucks;

    @BeforeEach
    void setUp() {

        this.service = new TruckService(dao);

        this.truck = new Truck(
                1L,
                "123",
                "Foo",
                "Foo",
                1F, Collections.emptySet()
        );

        this.trucks = new HashSet<>();
        trucks.add(truck);
    }

    @Test
    void findAll_WhenNoDataFound_ReturnsEmptySet() {

        when(dao.findAll()).thenReturn(Collections.emptySet());

        final var response = service.findAll();

        assertEquals(Collections.emptySet(), response);
    }

    @Test
    void findAll_WhenDataFound_ReturnsSetOfTrucks() {

        when(dao.findAll()).thenReturn(trucks);

        final var response = service.findAll();

        assertEquals(trucks, response);
    }

    @Test
    void findById_WhenIdDoesNotExist_ReturnsOptionalEmpty() {

        when(dao.findById(anyLong())).thenReturn(Optional.empty());

        final var response = service.findById(1L);

        assertEquals(Optional.empty(), response);
    }

    @Test
    void findById_WhenIdExists_ReturnsOptionalOfTruck() {

        when(dao.findById(anyLong())).thenReturn(Optional.of(truck));

        final var response = service.findById(1L);

        assertEquals(Optional.of(truck), response);
    }

    @Test
    void save_WhenTruckIdAlreadyExists_ThrowsExistingRecordException() {

        when(dao.findById(anyLong())).thenReturn(Optional.of(truck));

        assertThatExceptionOfType(ExistingRecordException.class)
                .isThrownBy(() -> service.save(truck))
                .withMessage("A truck with the provided ID already exists");
    }

    @Test
    void save_WhenTruckLicensePlateAlreadyExists_ThrowsExistingRecordException() {

        when(dao.findById(anyLong())).thenReturn(Optional.empty());
        when(dao.findByLicensePlate(anyString())).thenReturn(Optional.of(truck));

        assertThatExceptionOfType(ExistingRecordException.class)
                .isThrownBy(() -> service.save(truck))
                .withMessage("A truck with the provided License Plate already exists");
    }

    @Test
    void save_WhenTruckDataIsValid_SavesSuccessfully() {

        when(dao.save(any(Truck.class))).thenReturn(truck);

        final var response = service.save(truck);

        assertEquals(truck, response);
    }

    @Test
    void update_WhenIdDoesNotExists_ReturnOptionalEmpty() {

        when(dao.update(anyLong(), any(Truck.class))).thenReturn(Optional.empty());

        final var response = service.update(1L, truck);

        assertEquals(Optional.empty(), response);
    }

    @Test
    void update_WhenValidTruck_ReturnsOptionalOfUpdatedTruck() {

        final var updatedTruck = new Truck(
                1L,
                "ABC",
                "Foo updated",
                "Foo updated",
                2F,
                Collections.emptySet()
        );

        when(dao.update(anyLong(), any(Truck.class))).thenReturn(Optional.of(updatedTruck));

        final var response = service.update(1L, updatedTruck);

        assertEquals(Optional.of(updatedTruck), response);
    }

    @Test
    void delete_WhenInvalidId_ReturnsFalse() {

        when(dao.deleteById(anyLong())).thenReturn(false);

        final var response = service.delete(1L);

        assertFalse(response);
    }

    @Test
    void delete_WhenValidId_ReturnsTrue() {

        when(dao.deleteById(anyLong())).thenReturn(true);

        final var response = service.delete(1L);

        assertTrue(response);
    }

}
