package com.example.fleet_management.dao.entity;

import com.example.fleet_management.domain.Location;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "location")
public class LocationRow {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "latitude", nullable = false, precision = 8, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @OneToMany(mappedBy = "originRow", orphanRemoval = true)
    private Set<DeliveryOrderRow> deliveryOrderOriginRows;

    @OneToMany(mappedBy = "destinationRow", orphanRemoval = true)
    private Set<DeliveryOrderRow> deliveryOrderDestinationRows;

    public LocationRow(Long id, String name, BigDecimal latitude, BigDecimal longitude, Set<DeliveryOrderRow> deliveryOrderOriginRows, Set<DeliveryOrderRow> deliveryOrderDestinationRows) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deliveryOrderOriginRows = deliveryOrderOriginRows;
        this.deliveryOrderDestinationRows = deliveryOrderDestinationRows;
    }

    public LocationRow() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Set<DeliveryOrderRow> getDeliveryOrderOriginRows() {
        return deliveryOrderOriginRows;
    }

    public void setDeliveryOrderOriginRows(Set<DeliveryOrderRow> deliveryOrderOriginRows) {
        this.deliveryOrderOriginRows = deliveryOrderOriginRows;
    }

    public Set<DeliveryOrderRow> getDeliveryOrderDestinationRows() {
        return deliveryOrderDestinationRows;
    }

    public void setDeliveryOrderDestinationRows(Set<DeliveryOrderRow> deliveryOrderDestinationRows) {
        this.deliveryOrderDestinationRows = deliveryOrderDestinationRows;
    }

    public boolean addDeliveryOrderOriginRow(DeliveryOrderRow deliveryOrderRow) {

        if(this.deliveryOrderDestinationRows.isEmpty()) {
            this.deliveryOrderOriginRows = new HashSet<>();
        }

        return this.deliveryOrderOriginRows.add(deliveryOrderRow);
    }

    public boolean addDeliveryOrderDestinationRow(DeliveryOrderRow deliveryOrderRow) {

        if(this.deliveryOrderDestinationRows.isEmpty()) {
            this.deliveryOrderDestinationRows = new HashSet<>();
        }

        return this.deliveryOrderDestinationRows.add(deliveryOrderRow);
    }

    public static LocationRow toLocationRow(Location location) {

        final var deliveryOrderOrigins = location.deliveryOrderOrigins()
                .stream()
                .map(DeliveryOrderRow::toDeliveryOrderRow)
                .collect(Collectors.toSet());

        final var deliveryOrderDestinations = location.deliveryOrderDestinations()
                .stream()
                .map(DeliveryOrderRow::toDeliveryOrderRow)
                .collect(Collectors.toSet());

        return new LocationRow(
                location.id(),
                location.name(),
                location.latitude(),
                location.longitude(),
                deliveryOrderOrigins,
                deliveryOrderDestinations
        );
    }

    public Location toLocation() {

        return new Location(

                this.getId(),
                this.getName(),
                this.getLatitude(),
                this.getLongitude(),
                Collections.emptySet(),
                Collections.emptySet()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationRow that = (LocationRow) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!latitude.equals(that.latitude)) return false;
        if (!longitude.equals(that.longitude)) return false;
        if (!Objects.equals(deliveryOrderOriginRows, that.deliveryOrderOriginRows))
            return false;
        return Objects.equals(deliveryOrderDestinationRows, that.deliveryOrderDestinationRows);
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
        return "LocationRow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
