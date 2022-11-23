package com.example.fleet_management.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class DeliveryOrder {

    private final Long orderId;
    private final Truck truck;
    private final Location originLocation;
    private final Location deliveryLocation;
    private final Float distance;
    private final LocalDateTime timestamp;

    public DeliveryOrder(Long orderId, Truck truck, Location originLocation, Location deliveryLocation, Float distance, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.truck = truck;
        this.originLocation = originLocation;
        this.deliveryLocation = deliveryLocation;
        this.distance = distance;
        this.timestamp = timestamp;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Truck getTruck() {
        return truck;
    }

    public Location getOriginLocation() {
        return originLocation;
    }

    public Location getDeliveryLocation() {
        return deliveryLocation;
    }

    public Float getDistance() {
        return distance;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryOrder that = (DeliveryOrder) o;
        return orderId.equals(that.orderId) && truck.equals(that.truck) && originLocation.equals(that.originLocation) && deliveryLocation.equals(that.deliveryLocation) && distance.equals(that.distance) && timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, truck, originLocation, deliveryLocation, distance, timestamp);
    }

    @Override
    public String toString() {
        return "DeliveryOrder{" +
                "orderId=" + orderId +
                ", truck=" + truck +
                ", originLocation=" + originLocation +
                ", deliveryLocation=" + deliveryLocation +
                ", distance=" + distance +
                ", timestamp=" + timestamp +
                '}';
    }
}
