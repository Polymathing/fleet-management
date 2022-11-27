package com.example.fleet_management.web.dto.response;

import com.example.fleet_management.domain.Location;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record LocationResponseBody(Long id, String name, BigDecimal latitude, BigDecimal longitude) {

    public static LocationResponseBody fromLocation(Location location) {


        return new LocationResponseBody(
                location.getId(),
                location.getName(),
                location.getLatitude(),
                location.getLongitude()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationResponseBody that = (LocationResponseBody) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!latitude.equals(that.latitude)) return false;
        return longitude.equals(that.longitude);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LocationResponseBody{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
