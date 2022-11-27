package com.example.fleet_management.dao.entity;

import com.example.fleet_management.domain.DeliveryOrder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.fleet_management.dao.entity.LocationRow.toLocationRow;
import static com.example.fleet_management.dao.entity.TruckRow.toTruckRow;

@Entity
@Table(name = "delivery_order")
public class DeliveryOrderRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private TruckRow truckRow;

    @ManyToOne
    @JoinColumn(name = "origin_id", updatable = false)
    private LocationRow origin;

    @ManyToOne
    @JoinColumn(name = "destination_id", updatable = false)
    private LocationRow destination;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @CreationTimestamp
    @Column(name = "date_time", nullable = false, updatable = false)
    private LocalDateTime dateTime;

    public DeliveryOrderRow(Long id, TruckRow truckRow, LocationRow origin, LocationRow destination, Double distance, LocalDateTime dateTime) {
        this.id = id;
        this.truckRow = truckRow;
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.dateTime = dateTime;
    }

    public DeliveryOrderRow() {

    }

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

    public LocationRow getOrigin() {
        return origin;
    }

    public void setOrigin(LocationRow origin) {
        this.origin = origin;
    }

    public LocationRow getDestination() {
        return destination;
    }

    public void setDestination(LocationRow destination) {
        this.destination = destination;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static DeliveryOrderRow toDeliveryOrderRow(DeliveryOrder deliveryOrder) {

        final var truckRow = toTruckRow(deliveryOrder.truck());
        final var originRow = toLocationRow(deliveryOrder.origin());
        final var destinationRow = toLocationRow(deliveryOrder.destination());

        return new DeliveryOrderRow(
                deliveryOrder.id(),
                truckRow,
                originRow,
                destinationRow,
                deliveryOrder.distance(),
                deliveryOrder.timestamp()
        );
    }

    public DeliveryOrder toDeliveryOrder() {

        final var truck = this.getTruckRow().toTruck();
        final var origin = this.getOrigin().toLocation();
        final var destination = this.getDestination().toLocation();

        return new DeliveryOrder(

                this.getId(),
                truck,
                origin,
                destination,
                this.getDistance(),
                this.getDateTime()
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryOrderRow that = (DeliveryOrderRow) o;
        return Objects.equals(id, that.id) && truckRow.equals(that.truckRow) && origin.equals(that.origin) && destination.equals(that.destination) && distance.equals(that.distance) && dateTime.equals(that.dateTime);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (truckRow != null ? truckRow.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeliveryOrderRow{" +
                "id=" + id +
                ", truckRow=" + truckRow +
                ", origin=" + origin +
                ", destination=" + destination +
                ", distance=" + distance +
                ", dateTime=" + dateTime +
                '}';
    }
}
