package com.example.fleet_management.exception;

public class ExistingRecordException extends RuntimeException {

    public ExistingRecordException(String objName, String attribute) {
        super(String.format("A %s with the provided %s already exists", objName, attribute));
    }
}
