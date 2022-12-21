package com.example.fleet_management.domain.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class LocationGeoCoordinateValidatorTest {

    private LocationGeoCoordinateValidator locationGeoCoordinateValidator;

    @BeforeEach
    void setUp() { this.locationGeoCoordinateValidator = new LocationGeoCoordinateValidator(); }

    @Test
    void isCoordinateValid_WhenInvalidPattern_ReturnsFalse() {

        final var invalidPattern = "123.45678";

        final var response = locationGeoCoordinateValidator.isCoordinateValid(invalidPattern);

        assertFalse(response);
    }

    @Test
    void isCoordinateValid_WhenValidPattern_ReturnsTrue() {

        final var validPattern = "-27.345678";

        final var response = locationGeoCoordinateValidator.isCoordinateValid(validPattern);

        assertTrue(response);
    }
}
