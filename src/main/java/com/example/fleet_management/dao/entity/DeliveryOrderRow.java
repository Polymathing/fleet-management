package com.example.fleet_management.dao.entity;

import com.example.fleet_management.domain.DeliveryOrder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.example.fleet_management.dao.entity.LocationRow.toLocationRow;
import static com.example.fleet_management.dao.entity.TruckRow.toTruckRow;

@Entity
@Table(name = "delivery_order")
public class DeliveryOrderRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "truck_id", updatable = false)
    private TruckRow truckRow;

    @ManyToOne
    @JoinColumn(name = "origin_id", updatable = false)
    private LocationRow originRow;

    @ManyToOne
    @JoinColumn(name = "destination_id", updatable = false)
    private LocationRow destinationRow;

    @Column(name = "distance", nullable = false, updatable = false)
    private Double distance;

    @CreationTimestamp
    @Column(name = "date_time", nullable = false, updatable = false)
    private LocalDateTime dateTime;

    public DeliveryOrderRow(Long id, TruckRow truckRow, LocationRow originRow, LocationRow destinationRow, Double distance, LocalDateTime dateTime) {
        this.id = id;
        this.truckRow = truckRow;
        this.originRow = originRow;
        this.destinationRow = destinationRow;
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

    public LocationRow getOriginRow() {
        return originRow;
    }

    public void setOriginRow(LocationRow origin) {
        this.originRow = origin;
    }

    public LocationRow getDestinationRow() {
        return destinationRow;
    }

    public void setDestinationRow(LocationRow destination) {
        this.destinationRow = destination;
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
        final var origin = this.getOriginRow().toLocation();
        final var destination = this.getDestinationRow().toLocation();

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

        if (!id.equals(that.id)) return false;
        if (!truckRow.equals(that.truckRow)) return false;
        if (!originRow.equals(that.originRow)) return false;
        if (!destinationRow.equals(that.destinationRow)) return false;
        if (!distance.equals(that.distance)) return false;
        return dateTime.equals(that.dateTime);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (truckRow != null ? truckRow.hashCode() : 0);
        result = 31 * result + (originRow != null ? originRow.hashCode() : 0);
        result = 31 * result + (destinationRow != null ? destinationRow.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeliveryOrderRow{" +
                "id=" + id +
                ", truckRow=" + truckRow +
                ", originRow=" + originRow +
                ", destinationRow=" + destinationRow +
                ", distance=" + distance +
                ", dateTime=" + dateTime +
                '}';
    }
}
