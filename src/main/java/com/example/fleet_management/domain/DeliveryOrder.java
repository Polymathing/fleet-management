package com.example.fleet_management.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class DeliveryOrder {

    private final Long id;
    private final Truck truck;
    private final Location originLocation;
    private final Location deliveryLocation;
    private final Float distance;
    private final LocalDateTime timestamp;

    public DeliveryOrder(Long id, Truck truck, Location originLocation, Location deliveryLocation, Float distance, LocalDateTime timestamp) {
        this.id = id;
        this.truck = truck;
        this.originLocation = originLocation;
        this.deliveryLocation = deliveryLocation;
        this.distance = distance;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
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
        return id.equals(that.id) && truck.equals(that.truck) && originLocation.equals(that.originLocation) && deliveryLocation.equals(that.deliveryLocation) && distance.equals(that.distance) && timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, truck, originLocation, deliveryLocation, distance, timestamp);
    }

    @Override
    public String toString() {
        return "DeliveryOrder{" +
                "orderId=" + id +
                ", truck=" + truck +
                ", originLocation=" + originLocation +
                ", deliveryLocation=" + deliveryLocation +
                ", distance=" + distance +
                ", timestamp=" + timestamp +
                '}';
    }
}
