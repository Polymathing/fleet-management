package com.example.fleet_management.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String objName, String attribute) {
        super(String.format("A %s with the provided %s was not found", objName, attribute));
    }
}
