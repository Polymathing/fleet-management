package com.example.fleet_management.domain.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class LocationGeoCoordinateValidator {
    public static final Pattern GEOGRAPHIC_COORDINATE_PATTERN = Pattern.compile("^-?[0-9]{2}.[0-9]{6}$");

    public boolean isCoordinateValid(final String input) {

        return GEOGRAPHIC_COORDINATE_PATTERN
                .matcher(input)
                .matches();
    }
}
