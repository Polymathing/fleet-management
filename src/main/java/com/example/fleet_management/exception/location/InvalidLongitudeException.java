package com.example.fleet_management.exception.location;

public class InvalidLongitudeException extends RuntimeException {

    public InvalidLongitudeException(String longitude) {
        super(String.format("Longitude '%s' is invalid. " +
                "Pattern must be DECIMAL{9,6}, e.g. '123.456789'", longitude));
    }
}
