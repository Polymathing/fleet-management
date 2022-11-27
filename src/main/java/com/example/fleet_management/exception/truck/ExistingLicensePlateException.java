package com.example.fleet_management.exception.truck;

public class ExistingLicensePlateException extends RuntimeException {

    public ExistingLicensePlateException(String licensePlate) {
        super(String.format("A truck with the license plate %s already exists", licensePlate));
    }
}
