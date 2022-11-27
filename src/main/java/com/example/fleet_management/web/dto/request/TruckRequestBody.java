package com.example.fleet_management.web.dto.request;

import com.example.fleet_management.domain.Truck;

import java.util.Collections;

public record TruckRequestBody(Long id, String licensePlate, String manufacturer, String model, Float kilometersPerLiter) {

    public Truck toTruck() {

        return new Truck(
                this.id,
                this.licensePlate,
                this.manufacturer,
                this.model,
                this.kilometersPerLiter,
                Collections.emptySet()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TruckRequestBody that = (TruckRequestBody) o;

        if (!id.equals(that.id)) return false;
        if (!licensePlate.equals(that.licensePlate)) return false;
        if (!manufacturer.equals(that.manufacturer)) return false;
        if (!model.equals(that.model)) return false;
        return kilometersPerLiter.equals(that.kilometersPerLiter);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + licensePlate.hashCode();
        result = 31 * result + manufacturer.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + kilometersPerLiter.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TruckRequestBody{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", kilometersPerLiter=" + kilometersPerLiter +
                '}';
    }
}
