package com.example.fleet_management.domain.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class LocationLongitudeValidator {

    public static final Pattern LONGITUDE_PATTERN = Pattern.compile("^-?[0-9]{2}.[0-9]{6}$") ;

    public boolean isLongitudeValid(String input) {

        return LONGITUDE_PATTERN
                .matcher(input)
                .matches();
    }
}
