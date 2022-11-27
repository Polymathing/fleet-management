package com.example.fleet_management.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String attribute) {
        super(String.format("A %s with the provided ID was not found", attribute));
    }
}
