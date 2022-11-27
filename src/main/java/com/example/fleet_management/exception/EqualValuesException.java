package com.example.fleet_management.exception;

public class EqualValuesException extends RuntimeException {

    public EqualValuesException(String value1, String value2) {
        super(String.format("%s and %s cannot be the same.", value1, value2));
    }
}
