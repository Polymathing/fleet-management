package com.example.fleet_management.web.dto.request;

import com.example.fleet_management.domain.Location;

import java.math.BigDecimal;
import java.util.Collections;

public record LocationRequestBody(Long id, String name, BigDecimal latitude, BigDecimal longitude) {


    public Location toLocation() {

        return new Location(
                this.id,
                this.name,
                this.latitude,
                this.longitude,
                Collections.emptySet(),
                Collections.emptySet()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationRequestBody that = (LocationRequestBody) o;

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
        return "LocationRequestBody{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
