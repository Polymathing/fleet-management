package com.example.fleet_management.dao.entity;

import com.example.fleet_management.domain.Location;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "location")
@EntityListeners(AuditingEntityListener.class)
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

    @OneToMany(mappedBy = "origin")
    private Set<DeliveryOrderRow> deliveryOrderOriginRows;

    @OneToMany(mappedBy = "destination")
    private Set<DeliveryOrderRow> deliveryOrderDestinationRows;

    public LocationRow(Long id, String name, BigDecimal latitude, BigDecimal longitude, Set<DeliveryOrderRow> deliveryOrderOriginRows, Set<DeliveryOrderRow> deliveryOrderDestinationRows) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deliveryOrderOriginRows = deliveryOrderOriginRows;
        this.deliveryOrderDestinationRows = deliveryOrderDestinationRows;
    }

    public LocationRow() {

    }

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

    public static LocationRow toLocationRow(Location location) {

        final var deliveryOrderOrigins = location.getDeliveryOrderOrigins()
                .stream()
                .map(DeliveryOrderRow::toDeliveryOrderRow)
                .collect(Collectors.toSet());

        final var deliveryOrderDestinations = location.getDeliveryOrderDestinations()
                .stream()
                .map(DeliveryOrderRow::toDeliveryOrderRow)
                .collect(Collectors.toSet());

        return new LocationRow(
                location.getId(),
                location.getName(),
                location.getLatitude(),
                location.getLongitude(),
                deliveryOrderOrigins,
                deliveryOrderDestinations
        );
    }

    public Location toLocation() {

        final var deliveryOrderOrigins = this.getDeliveryOrderOriginRows()
                .stream()
                .map(DeliveryOrderRow::toDeliveryOrder)
                .collect(Collectors.toSet());

        final var deliveryOrderDestinations = this.getDeliveryOrderDestinationRows()
                .stream()
                .map(DeliveryOrderRow::toDeliveryOrder)
                .collect(Collectors.toSet());

        return new Location(

                this.getId(),
                this.getName(),
                this.getLatitude(),
                this.getLongitude(),
                deliveryOrderOrigins,
                deliveryOrderDestinations
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationRow that = (LocationRow) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(deliveryOrderOriginRows, that.deliveryOrderOriginRows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latitude, longitude, deliveryOrderOriginRows);
    }

    @Override
    public String toString() {
        return "LocationRow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", deliveryOrderRows=" + deliveryOrderOriginRows +
                '}';
    }
}
