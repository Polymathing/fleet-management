package com.example.fleet_management.exception.location;

public class InvalidCoordinateException extends RuntimeException {

    public InvalidCoordinateException(String coordinate, String value) {
        super(String.format("%s '%s' is invalid. " +
                "Pattern must be DECIMAL{8,6}, e.g. '12.345678'", coordinate, value));
    }
}
