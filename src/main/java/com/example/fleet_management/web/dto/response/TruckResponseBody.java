package com.example.fleet_management.web.dto.response;

import com.example.fleet_management.domain.Truck;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public record TruckResponseBody(Long id, String licensePlate, String manufacturer, String model, Float kilometersPerLiter, Set<DeliveryOrderResponseBody> deliveryOrders) {

    public static TruckResponseBody fromTruck(Truck truck) {

        final var deliveryOrders = truck.getDeliveryOrderSet()
                .stream()
                .map(DeliveryOrderResponseBody::fromDeliveryOrder)
                .collect(Collectors.toSet());

        return new TruckResponseBody(
                truck.getId(),
                truck.getLicensePlate(),
                truck.getManufacturer(),
                truck.getModel(),
                truck.getKilometersPerLiter(),
                deliveryOrders
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckResponseBody that = (TruckResponseBody) o;
        return Objects.equals(id, that.id) && Objects.equals(licensePlate, that.licensePlate) && Objects.equals(manufacturer, that.manufacturer) && Objects.equals(model, that.model) && Objects.equals(kilometersPerLiter, that.kilometersPerLiter) && Objects.equals(deliveryOrders, that.deliveryOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, licensePlate, manufacturer, model, kilometersPerLiter, deliveryOrders);
    }

    @Override
    public String toString() {
        return "TruckResponseBody{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", kilometersPerLiter=" + kilometersPerLiter +
                ", deliveryOrders=" + deliveryOrders +
                '}';
    }
}
