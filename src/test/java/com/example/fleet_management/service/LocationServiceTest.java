package com.example.fleet_management.service;

import com.example.fleet_management.dao.LocationDAO;
import com.example.fleet_management.domain.Location;
import com.example.fleet_management.domain.service.LocationService;
import com.example.fleet_management.domain.validator.LocationLatitudeValidator;
import com.example.fleet_management.domain.validator.LocationLongitudeValidator;
import com.example.fleet_management.exception.ExistingRecordException;
import com.example.fleet_management.exception.location.InvalidLatitudeException;
import com.example.fleet_management.exception.location.InvalidLongitudeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class LocationServiceTest {

    private LocationService service;

    @Mock
    private LocationDAO dao;

    @Mock
    private LocationLongitudeValidator longitudeValidator;
    @Mock
    private LocationLatitudeValidator latitudeValidator;

    private Location location;
    private Set<Location> locations;

    @BeforeEach
    void setUp() {

        this.service = new LocationService(this.dao, latitudeValidator, longitudeValidator);

        this.location = new Location(1L, "Foo", BigDecimal.valueOf(1), BigDecimal.valueOf(2), Collections.emptySet(), Collections.emptySet());
        this.locations = new HashSet<>();
        locations.add(location);
    }

    @Test
    void findAll_WhenNoData_ReturnsEmptySet() {

        when(dao.findAll()).thenReturn(Collections.emptySet());

        final var response = service.findAll();

        assertEquals(Collections.emptySet(), response);
    }

    @Test
    void findAll_WhenDataFound_ReturnsSetOfLocation() {

        when(dao.findAll()).thenReturn(locations);

        final var response = service.findAll();

        assertEquals(locations, response);
    }

    @Test
    void findById_WhenIdDoesNotExist_ReturnsOptionalEmpty() {

        when(dao.findById(anyLong())).thenReturn(Optional.empty());

        final var response = service.findById(1L);

        assertEquals(Optional.empty(), response);
    }

    @Test
    void findById_WhenIdExists_ReturnsOptionalOfLocation() {

        when(dao.findById(anyLong())).thenReturn(Optional.of(location));

        final var response = service.findById(1L);

        assertEquals(Optional.of(location), response);
    }

    @Test
    void save_WhenLocationIdAlreadyExists_ThrowsExistingRecordException() {

        when(dao.findById(anyLong())).thenReturn(Optional.of(location));

        assertThatExceptionOfType(ExistingRecordException.class)
                .isThrownBy(() -> service.save(location))
                .withMessage("A location with the provided ID already exists");
    }

    @Test
    void save_WhenLocationNameAlreadyExists_ThrowsExistingRecordException() {

        when(dao.findById(anyLong())).thenReturn(Optional.empty());
        when(dao.findByName(anyString())).thenReturn(Optional.of(location));

        assertThatExceptionOfType(ExistingRecordException.class)
                .isThrownBy(() -> service.save(location))
                .withMessage("A location with the provided name already exists");
    }

    @Test
    void save_WhenLocationLatitudeIsInvalid_ThrowsInvalidLatitudeException() {

        when(dao.findById(anyLong())).thenReturn(Optional.empty());
        when(dao.findByName(anyString())).thenReturn(Optional.empty());
        when(latitudeValidator.isLatitudeValid(anyString())).thenReturn(false);

        assertThatExceptionOfType(InvalidLatitudeException.class)
                .isThrownBy(() -> service.save(location))
                .withMessage("Latitude '1' is invalid. Pattern must be DECIMAL{8,6}, e.g. '12.345678'");
    }

    @Test
    void save_WhenLocationLongitudeIsInvalid_ThrowsInvalidLongitudeException() {

        when(dao.findById(anyLong())).thenReturn(Optional.empty());
        when(dao.findByName(anyString())).thenReturn(Optional.empty());
        when(latitudeValidator.isLatitudeValid(anyString())).thenReturn(true);
        when(longitudeValidator.isLongitudeValid(anyString())).thenReturn(false);

        assertThatExceptionOfType(InvalidLongitudeException.class)
                .isThrownBy(() -> service.save(location))
                .withMessage("Longitude '2' is invalid. Pattern must be DECIMAL{9,6}, e.g. '123.456789'");
    }

    @Test
    void save_WhenLocationIsValid_SavesSuccessfully() {

        when(dao.findById(anyLong())).thenReturn(Optional.empty());
        when(dao.findByName(anyString())).thenReturn(Optional.empty());
        when(latitudeValidator.isLatitudeValid(anyString())).thenReturn(true);
        when(longitudeValidator.isLongitudeValid(anyString())).thenReturn(true);
        when(dao.save(any(Location.class))).thenReturn(location);

        final var response = service.save(location);

        assertEquals(location, response);
    }

    @Test
    void update_WhenIdDoesNotExists_ReturnOptionalEmpty() {

        when(dao.update(anyLong(), any(Location.class))).thenReturn(Optional.empty());

        final var response = service.update(1L, location);

        assertEquals(Optional.empty(), response);
    }

    @Test
    void update_WhenValidLocation_ReturnsOptionalOfUpdatedLocation() {

        final var updatedLocation = new Location(
                1L,
                "Foo updated",
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(4),
                Collections.emptySet(),
                Collections.emptySet()
        );

        when(dao.update(anyLong(), any(Location.class))).thenReturn(Optional.of(updatedLocation));

        final var response = service.update(1L, updatedLocation);

        assertEquals(Optional.of(updatedLocation), response);
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