package com.example.fleet_management.dao;

import com.example.fleet_management.dao.entity.TruckRow;
import com.example.fleet_management.dao.repository.TruckRepository;
import com.example.fleet_management.domain.Truck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TruckDAOTest {

    private TruckDAO dao;

    @Mock
    private TruckRepository repository;

    private TruckRow truckRow;
    private Truck truck;
    private Set<Truck> truckSet;

    @BeforeEach
    void setUp() {

        this.dao = new TruckDAO(repository);

        this.truckRow = new TruckRow(
                1L,
                "12345678",
                "23K",
                "Volvo",
                10.5F,
                Collections.emptySet()
        );

        this.truck = truckRow.toTruck();

        this.truckSet = new HashSet<>();
        this.truckSet.add(truck);
    }

    @Test
    void findAll_WithoutTrucks_ReturnsEmptySet() {

        when(repository.findAll())
                .thenReturn(Collections.emptyList());

        final var response = dao.findAll();

        assertEquals(Collections.emptySet(), response);
    }

    @Test
    void findAll_HavingSavedTrucks_ReturnsTruckSet() {

        when(repository.findAll())
                .thenReturn(List.of(truckRow));

        final var response = dao.findAll();

        assertEquals(truckSet, response);
    }

    @Test
    void findById_WhenTruckNotFound_ReturnsEmptyOptional() {

        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());

        final var response = dao.findById(1L);

        assertEquals(Optional.empty(), response);
    }

    @Test
    void findById_WhenTruckIsFound_ReturnsOptionalOfTruck() {

        final var opTruckRow = Optional.of(truckRow);

        when(repository.findById(anyLong()))
                .thenReturn(opTruckRow);

        final var response = dao.findById(1L);

        final var opTruck = Optional.of(truck);

        assertEquals(opTruck, response);
    }

    @Test
    void findByLicensePlate_WhenTruckIsNotFound_ReturnsEmptyOptional() {

        when(repository.findByLicensePlate(anyString()))
                .thenReturn(Optional.empty());

        final var response = dao.findByLicensePlate("123");

        assertEquals(Optional.empty(), response);
    }

    @Test
    void findByLicensePlate_WhenTruckIsFound_ReturnsOptionalOfTruck() {

        final var opTruckRow = Optional.of(truckRow);

        when(repository.findByLicensePlate(anyString()))
                .thenReturn(opTruckRow);

        final var response = dao.findByLicensePlate("123");

        final var opTruck = Optional.of(truck);

        assertEquals(opTruck, response);
    }

    @Test
    void save_WhenValidTruck_ReturnsSavedTruck() {

        when(repository.save(any()))
                .thenReturn(truckRow);

        final var response = dao.save(truck);

        assertEquals(truck, response);
    }

    @Test
    void update_WhenTruckIsFound_ReturnsUpdatedTruck() {

        final var opTruckRow = Optional.of(truckRow);

        when(repository.findById(anyLong()))
                .thenReturn(opTruckRow);

        final var response = dao.update(1L, truck);
    }

    @Test
    void update_WhenTruckIsNotFound_ReturnsOptionalEmpty() {

        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());

        final var response = dao.update(1L, truck);

        assertEquals(Optional.empty(), response);
    }

    @Test
    void delete_WhenTruckIsFoundAndDeleted_ReturnsTrue() {

        final var opTruckRow = Optional.of(truckRow);

        when(repository.findById(anyLong()))
                .thenReturn(opTruckRow);

        final var response = dao.deleteById(1L);

        assertTrue(response);
    }

    @Test
    void delete_WhenTruckIsNotFound_ReturnsFalse() {

        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());

        final var response = dao.deleteById(1L);

        assertFalse(response);
    }
}
