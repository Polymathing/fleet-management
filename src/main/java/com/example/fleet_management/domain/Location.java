package com.example.fleet_management.domain;

import java.math.BigDecimal;
import java.util.Set;

public record Location(Long id, String name, BigDecimal latitude, BigDecimal longitude,
                       Set<DeliveryOrder> deliveryOrderOrigins, Set<DeliveryOrder> deliveryOrderDestinations) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id.equals(location.id) && name.equals(location.name) && latitude.equals(location.latitude) && longitude.equals(location.longitude) && deliveryOrderOrigins.equals(location.deliveryOrderOrigins) && deliveryOrderDestinations.equals(location.deliveryOrderDestinations);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", deliveryOrderOrigins=" + deliveryOrderOrigins +
                ", deliveryOrderDestinations=" + deliveryOrderDestinations +
                '}';
    }
}
