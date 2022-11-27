package com.example.fleet_management.domain;

import java.time.LocalDateTime;

public record DeliveryOrder(Long id, Truck truck, Location origin, Location destination, Double distance,
                            LocalDateTime timestamp) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryOrder that = (DeliveryOrder) o;

        if (!id.equals(that.id)) return false;
        if (!truck.equals(that.truck)) return false;
        if (!origin.equals(that.origin)) return false;
        if (!destination.equals(that.destination)) return false;
        if (!distance.equals(that.distance)) return false;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public String toString() {
        return "DeliveryOrder{" +
                "id=" + id +
                ", truck=" + truck +
                ", origin=" + origin +
                ", destination=" + destination +
                ", distance=" + distance +
                ", createdAt=" + timestamp +
                '}';
    }
}
