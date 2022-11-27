package com.example.fleet_management.web.dto.response;

import com.example.fleet_management.domain.DeliveryOrder;

import java.time.LocalDateTime;

public record DeliveryOrderResponseBody(Long id, TruckResponseBody truck, LocationResponseBody originLocation, LocationResponseBody destination, Float distance, LocalDateTime timestamp) {

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
        if (!originLocation.equals(that.originLocation)) return false;
        if (!destination.equals(that.destination)) return false;
        if (!distance.equals(that.distance)) return false;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + truck.hashCode();
        result = 31 * result + originLocation.hashCode();
        result = 31 * result + destination.hashCode();
        result = 31 * result + distance.hashCode();
        result = 31 * result + timestamp.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DeliveryOrderResponseBody{" +
                "id=" + id +
                ", truck=" + truck +
                ", originLocation=" + originLocation +
                ", destination=" + destination +
                ", distance=" + distance +
                ", timestamp=" + timestamp +
                '}';
    }
}
