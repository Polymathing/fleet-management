package com.example.fleet_management.exception.location;

public class InvalidLatitudeException extends RuntimeException {

    public InvalidLatitudeException(String latitude) {
        super(String.format("Latitude '%s' is invalid. " +
                "Pattern must be DECIMAL{8,6}, e.g. '12.345678'", latitude));
    }
}
