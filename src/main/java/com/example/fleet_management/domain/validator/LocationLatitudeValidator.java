package com.example.fleet_management.domain.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class LocationLatitudeValidator {
    public static final Pattern LATITUDE_PATTERN = Pattern.compile("^-?[0-9]{2}.[0-9]{6}$");

    public boolean isLatitudeValid(final String input) {

        return LATITUDE_PATTERN
                .matcher(input)
                .matches();
    }
}
