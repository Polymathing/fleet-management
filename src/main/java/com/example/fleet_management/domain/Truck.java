package com.example.fleet_management.domain;

import java.util.Objects;
import java.util.Set;

public class Truck {

    private final Long truckId;
    private final String licensePlate;
    private final String manufacturer;
    private final String model;
    private final Float kilometersPerLiter;
    private final Set<DeliveryOrder> deliveryOrderSet;

    public Truck(Long truckId, String licensePlate, String manufacturer, String model, Float kilometersPerLiter, Set<DeliveryOrder> deliveryOrderSet) {
        this.truckId = truckId;
        this.licensePlate = licensePlate;
        this.manufacturer = manufacturer;
        this.model = model;
        this.kilometersPerLiter = kilometersPerLiter;
        this.deliveryOrderSet = deliveryOrderSet;
    }

    public Long getTruckId() {
        return truckId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public Float getKilometersPerLiter() {
        return kilometersPerLiter;
    }

    public Set<DeliveryOrder> getDeliveryOrderSet() {
        return deliveryOrderSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return Objects.equals(truckId, truck.truckId) && Objects.equals(licensePlate, truck.licensePlate) && Objects.equals(manufacturer, truck.manufacturer) && Objects.equals(model, truck.model) && Objects.equals(kilometersPerLiter, truck.kilometersPerLiter) && Objects.equals(deliveryOrderSet, truck.deliveryOrderSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(truckId, licensePlate, manufacturer, model, kilometersPerLiter, deliveryOrderSet);
    }

    @Override
    public String toString() {
        return "Truck{" +
                "truckId=" + truckId +
                ", licensePlate='" + licensePlate + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", kilometersPerLiter=" + kilometersPerLiter +
                ", deliveryOrderSet=" + deliveryOrderSet +
                '}';
    }
}
