package com.example.fleet_management.web.dto.response;

import com.example.fleet_management.domain.Truck;

public record TruckResponseBody(Long id, String licensePlate, String manufacturer, String model, Float kilometersPerLiter) {

    public static TruckResponseBody fromTruck(Truck truck) {

        return new TruckResponseBody(
                truck.id(),
                truck.licensePlate(),
                truck.manufacturer(),
                truck.model(),
                truck.kilometersPerLiter()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TruckResponseBody that = (TruckResponseBody) o;

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
        return "TruckResponseBody{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", kilometersPerLiter=" + kilometersPerLiter +
                '}';
    }
}
