package com.example.fleet_management.dao.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

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

    @OneToMany(mappedBy = "truckRow")
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
