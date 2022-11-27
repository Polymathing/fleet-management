package com.example.fleet_management.exception;

public class ExistingRecordException extends RuntimeException {

    public ExistingRecordException(String attribute) {
        super(String.format("A %s with the provided ID already exists", attribute));
    }
}
