package com.example.fleet_management.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class Location {

    private final Long id;
    private final String name;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final Set<DeliveryOrder> deliveryOrderOrigins;
    private Set<DeliveryOrder> deliveryOrderDestinations;

    public Location(Long id, String name, BigDecimal latitude, BigDecimal longitude, Set<DeliveryOrder> deliveryOrderOrigins, Set<DeliveryOrder> deliveryOrderDestinations) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deliveryOrderOrigins = deliveryOrderOrigins;
        this.deliveryOrderDestinations = deliveryOrderDestinations;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public Set<DeliveryOrder> getDeliveryOrderOrigins() {
        return deliveryOrderOrigins;
    }

    public Set<DeliveryOrder> getDeliveryOrderDestinations() {
        return deliveryOrderDestinations;
    }

    public void setDeliveryOrderDestinations(Set<DeliveryOrder> deliveryOrderDestinations) {
        this.deliveryOrderDestinations = deliveryOrderDestinations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id.equals(location.id) && name.equals(location.name) && latitude.equals(location.latitude) && longitude.equals(location.longitude) && deliveryOrderOrigins.equals(location.deliveryOrderOrigins) && deliveryOrderDestinations.equals(location.deliveryOrderDestinations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latitude, longitude, deliveryOrderOrigins, deliveryOrderDestinations);
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
