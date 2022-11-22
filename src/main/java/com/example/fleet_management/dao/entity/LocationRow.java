package com.example.fleet_management.dao.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "location")
public class LocationRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    @OneToMany
    private Set<DeliveryOrderRow> deliveryOrderRows;

    public LocationRow(Long id, String name, BigDecimal latitude, BigDecimal longitude, Set<DeliveryOrderRow> deliveryOrderRows) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deliveryOrderRows = deliveryOrderRows;
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

    public Set<DeliveryOrderRow> getDeliveryOrderRows() {
        return deliveryOrderRows;
    }

    public void setDeliveryOrderRows(Set<DeliveryOrderRow> deliveryOrderRows) {
        this.deliveryOrderRows = deliveryOrderRows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationRow that = (LocationRow) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(deliveryOrderRows, that.deliveryOrderRows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latitude, longitude, deliveryOrderRows);
    }

    @Override
    public String toString() {
        return "LocationRow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", deliveryOrderRows=" + deliveryOrderRows +
                '}';
    }
}
