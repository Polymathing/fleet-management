package com.example.fleet_management.dao.entity;

import com.example.fleet_management.domain.DeliveryOrder;
import com.example.fleet_management.domain.Truck;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "truck")
public class TruckRow {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NaturalId
    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "kilometers_per_liter", nullable = false)
    private Float kilometersPerLiter;

    @OneToMany(mappedBy = "truckRow", orphanRemoval = true)
    private Set<DeliveryOrderRow> deliveryOrderRows;

    public TruckRow(Long id, String licensePlate, String manufacturer, String model, Float kilometersPerLiter, Set<DeliveryOrderRow> deliveryOrderRows) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.manufacturer = manufacturer;
        this.model = model;
        this.kilometersPerLiter = kilometersPerLiter;
        this.deliveryOrderRows = deliveryOrderRows;
    }

    public TruckRow() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getKilometersPerLiter() {
        return kilometersPerLiter;
    }

    public void setKilometersPerLiter(Float kilometersPerLiter) {
        this.kilometersPerLiter = kilometersPerLiter;
    }

    public Set<DeliveryOrderRow> getDeliveryOrderRows() {
        return deliveryOrderRows;
    }

    public void setDeliveryOrderRows(Set<DeliveryOrderRow> deliveryOrderRows) {
        this.deliveryOrderRows = deliveryOrderRows;
    }

    public boolean addDeliveryOrderRecord(DeliveryOrderRow deliveryOrderRow) {

        if(this.deliveryOrderRows == null) {
            this.deliveryOrderRows = new HashSet<>();
        }

        return this.deliveryOrderRows.add(deliveryOrderRow);
    }

    public static TruckRow toTruckRow(Truck truck) {

        final var deliveryOrderRows = truck.getDeliveryOrderSet()
                .stream()
                .map(DeliveryOrderRow::toDeliveryOrderRow)
                .collect(Collectors.toSet());

        return new TruckRow(
                truck.getId(),
                truck.getLicensePlate(),
                truck.getManufacturer(),
                truck.getModel(),
                truck.getKilometersPerLiter(),
                deliveryOrderRows
        );
    }

    public Truck toTruck() {

        return new Truck(
                this.getId(),
                this.getLicensePlate(),
                this.getManufacturer(),
                this.getModel(),
                this.getKilometersPerLiter(),
                Collections.emptySet()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckRow truckRow = (TruckRow) o;
        return Objects.equals(id, truckRow.id) && Objects.equals(licensePlate, truckRow.licensePlate) && Objects.equals(manufacturer, truckRow.manufacturer) && Objects.equals(model, truckRow.model) && Objects.equals(kilometersPerLiter, truckRow.kilometersPerLiter) && Objects.equals(deliveryOrderRows, truckRow.deliveryOrderRows);
    }

    @Override
    public int hashCode() {
        return 13;
    }

    @Override
    public String toString() {
        return "TruckRow{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", kilometersPerLiter=" + kilometersPerLiter +
                ", deliveryOrderRows=" + deliveryOrderRows +
                '}';
    }
}
