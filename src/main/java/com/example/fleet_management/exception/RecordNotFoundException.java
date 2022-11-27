package com.example.fleet_management.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String attribute) {
        super(String.format("A %s with the provided ID was not found", attribute));
    }
}
