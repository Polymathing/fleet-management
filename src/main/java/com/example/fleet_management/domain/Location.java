package com.example.fleet_management.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Set;

public class Location {

    private final Long locationId;
    private final String name;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final Set<DeliveryOrder> deliveryOrderSet;

    public Location(Long locationId, String name, BigDecimal latitude, BigDecimal longitude, Set<DeliveryOrder> deliveryOrderSet) {
        this.locationId = locationId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deliveryOrderSet = deliveryOrderSet;
    }

    public Long getLocationId() {
        return locationId;
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

    public Set<DeliveryOrder> getDeliveryOrderSet() {
        return deliveryOrderSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationId.equals(location.locationId) && name.equals(location.name) && latitude.equals(location.latitude) && longitude.equals(location.longitude) && deliveryOrderSet.equals(location.deliveryOrderSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, name, latitude, longitude, deliveryOrderSet);
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", deliveryOrderSet=" + deliveryOrderSet +
                '}';
    }
}
