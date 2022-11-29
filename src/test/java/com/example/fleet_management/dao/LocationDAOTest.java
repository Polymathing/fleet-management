package com.example.fleet_management.dao;

import com.example.fleet_management.dao.entity.LocationRow;
import com.example.fleet_management.dao.repository.LocationRepository;
import com.example.fleet_management.domain.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class LocationDAOTest {

    private LocationDAO dao;

    @Mock
    private LocationRepository repository;

    private LocationRow locationRow;

    private Location location;
    private Set<Location> locationSet;

    @BeforeEach
    void setUp() {

        this.dao = new LocationDAO(repository);

        this.locationRow = new LocationRow(
                1L,
                "Foo",
                BigDecimal.valueOf(12.345678),
                BigDecimal.valueOf(123.456789),
                Collections.emptySet(),
                Collections.emptySet()

        );

        this.location = locationRow.toLocation();

        this.locationSet = new HashSet<>();
        this.locationSet.add(location);
    }

    @Test
    void findAll_NotHavingLocationsSaved_ReturnsEmptySet() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        final var response = dao.findAll();

        assertEquals(Collections.emptySet(), response);

    }

    @Test
    void findAll_HavingLocationsSaved_ReturnsAllLocationsSaved() {

        when(repository.findAll()).thenReturn(List.of(locationRow));

        final var response = dao.findAll();

        assertEquals(locationSet, response);
    }

    @Test
    void findById_WhenLocationNotFound_ReturnsOptionalEmpty() {

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        final var response = dao.findById(1L);

        assertEquals(Optional.empty(), response);
    }

    @Test
    void findById_WhenLocationIsFound_ReturnsOptionalLocation() {

        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(locationRow));

        final var response = dao.findById(1L);

        assertEquals(
                Optional.of(location),
                response);
    }

    @Test
    void findByName_WhenLocationNotFound_ReturnsOptionalEmpty() {

        when(repository.findByName(anyString())).thenReturn(Optional.empty());

        final var response = dao.findByName("Foo");

        assertEquals(Optional.empty(), response);
    }

    @Test
    void findByName_WhenLocationIsFound_ReturnsOptionalLocation() {

        when(repository.findByName(anyString()))
                .thenReturn(Optional.of(locationRow));

        final var response = dao.findByName("Foo");

        assertEquals(
                Optional.of(location),
                response);
    }


    @Test
    void save_WhenValidLocation_ReturnsSavedLocation() {

        when(repository.save(any(LocationRow.class)))
                .thenReturn(locationRow);

        final var response = dao.save(location);

        assertEquals(location, response);
    }

    @Test
    void update_WhenValidLocation_ReturnsUpdatedLocation() {

        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(locationRow));

        final var response = dao.update(1L, location);

        assertEquals(Optional.of(location), response);
    }

    @Test
    void delete_WhenIdIsFound_ReturnsTrueForDeleted() {

        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(locationRow));

        final var response = dao.deleteById(1L);

        assertTrue(response);
    }

    @Test
    void delete_WhenIdIsNotFound_ReturnsFalseForDeleted() {

        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());

        final var response = dao.deleteById(1L);

        assertFalse(response);
    }
}
