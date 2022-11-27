package com.example.fleet_management.web.dto.response;

import com.example.fleet_management.domain.DeliveryOrder;

import java.time.LocalDateTime;

public record DeliveryOrderResponseBody(Long id, TruckResponseBody truck, LocationResponseBody origin, LocationResponseBody destination, Double distanceInKM, LocalDateTime createdAt) {

    public static DeliveryOrderResponseBody fromDeliveryOrder(DeliveryOrder deliveryOrder) {

        final var truck = TruckResponseBody.fromTruck(deliveryOrder.truck());
        final var origin = LocationResponseBody.fromLocation(deliveryOrder.origin());
        final var destination = LocationResponseBody.fromLocation(deliveryOrder.destination());

        return new DeliveryOrderResponseBody(
                deliveryOrder.id(),
                truck,
                origin,
                destination,
                deliveryOrder.distance(),
                deliveryOrder.timestamp()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryOrderResponseBody that = (DeliveryOrderResponseBody) o;

        if (!id.equals(that.id)) return false;
        if (!truck.equals(that.truck)) return false;
        if (!origin.equals(that.origin)) return false;
        if (!destination.equals(that.destination)) return false;
        if (!distanceInKM.equals(that.distanceInKM)) return false;
        return createdAt.equals(that.createdAt);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + truck.hashCode();
        result = 31 * result + origin.hashCode();
        result = 31 * result + destination.hashCode();
        result = 31 * result + distanceInKM.hashCode();
        result = 31 * result + createdAt.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DeliveryOrderResponseBody{" +
                "id=" + id +
                ", truck=" + truck +
                ", origin=" + origin +
                ", destination=" + destination +
                ", distanceInKM=" + distanceInKM +
                ", createdAt=" + createdAt +
                '}';
    }
}
