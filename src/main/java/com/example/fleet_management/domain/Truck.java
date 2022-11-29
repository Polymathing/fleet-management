package com.example.fleet_management.domain;

import java.util.Objects;
import java.util.Set;

public record Truck(Long id, String licensePlate, String manufacturer,
                    String model, Float kilometersPerLiter,
                    Set<DeliveryOrder> deliveryOrderSet) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return Objects.equals(id, truck.id) && Objects.equals(licensePlate, truck.licensePlate) && Objects.equals(manufacturer, truck.manufacturer) && Objects.equals(model, truck.model) && Objects.equals(kilometersPerLiter, truck.kilometersPerLiter) && Objects.equals(deliveryOrderSet, truck.deliveryOrderSet);
    }

    @Override
    public String toString() {
        return "Truck{" +
                "truckId=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", kilometersPerLiter=" + kilometersPerLiter +
                ", deliveryOrderSet=" + deliveryOrderSet +
                '}';
    }
}
