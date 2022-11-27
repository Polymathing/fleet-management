package com.example.fleet_management.domain.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class LocationLongitudeValidator {

    public static final Pattern LONGITUDE_PATTERN = Pattern.compile("^\\d{3}.\\d{6}$") ;

    public boolean isLatitudeValid(String input) {

        return LONGITUDE_PATTERN
                .matcher(input)
                .matches();
    }
}
