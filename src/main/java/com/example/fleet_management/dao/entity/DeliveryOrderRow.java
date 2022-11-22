package com.example.fleet_management.dao.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "delivery_order")
public class DeliveryOrderRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private TruckRow truckRow;

    @ManyToOne
    @JoinColumn(name = "origin_location_id")
    private LocationRow originLocation;

    @ManyToOne
    @JoinColumn(name = "delivery_location_id")
    private LocationRow deliveryLocation;

    @Column(name = "distance", nullable = false)
    private Float distance;

    @CreationTimestamp
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    public DeliveryOrderRow(Long id, TruckRow truckRow, LocationRow originLocation, LocationRow deliveryLocation, Float distance, LocalDateTime dateTime) {
        this.id = id;
        this.truckRow = truckRow;
        this.originLocation = originLocation;
        this.deliveryLocation = deliveryLocation;
        this.distance = distance;
        this.dateTime = dateTime;
    }

    public DeliveryOrderRow() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TruckRow getTruckRow() {
        return truckRow;
    }

    public void setTruckRow(TruckRow truckRow) {
        this.truckRow = truckRow;
    }

    public LocationRow getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(LocationRow originLocation) {
        this.originLocation = originLocation;
    }

    public LocationRow getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(LocationRow deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryOrderRow that = (DeliveryOrderRow) o;
        return Objects.equals(id, that.id) && Objects.equals(truckRow, that.truckRow) && Objects.equals(originLocation, that.originLocation) && Objects.equals(deliveryLocation, that.deliveryLocation) && Objects.equals(distance, that.distance) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, truckRow, originLocation, deliveryLocation, distance, dateTime);
    }

    @Override
    public String toString() {
        return "DeliveryOrderRow{" +
                "id=" + id +
                ", truckRow=" + truckRow +
                ", originLocation=" + originLocation +
                ", deliveryLocation=" + deliveryLocation +
                ", distance=" + distance +
                ", dateTime=" + dateTime +
                '}';
    }
}