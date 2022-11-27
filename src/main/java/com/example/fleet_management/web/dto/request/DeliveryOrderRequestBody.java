package com.example.fleet_management.web.dto.request;

import com.example.fleet_management.domain.DeliveryOrder;
import com.example.fleet_management.web.dto.response.LocationResponseBody;

import java.time.LocalDateTime;

public record DeliveryOrderRequestBody(Long id, TruckRequestBody truck, LocationRequestBody originLocation, LocationResponseBody deliveryLocation, Float distance, LocalDateTime timestamp) {


    public DeliveryOrder toDeliveryOrder() {

        return new DeliveryOrder(
                this.id,
                null,
                null,
                null,
                this.distance,
                this.timestamp
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryOrderRequestBody that = (DeliveryOrderRequestBody) o;

        if (!id.equals(that.id)) return false;
        if (!truck.equals(that.truck)) return false;
        if (!originLocation.equals(that.originLocation)) return false;
        if (!deliveryLocation.equals(that.deliveryLocation)) return false;
        if (!distance.equals(that.distance)) return false;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + truck.hashCode();
        result = 31 * result + originLocation.hashCode();
        result = 31 * result + deliveryLocation.hashCode();
        result = 31 * result + distance.hashCode();
        result = 31 * result + timestamp.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DeliveryOrderRequestBody{" +
                "id=" + id +
                ", truck=" + truck +
                ", originLocation=" + originLocation +
                ", destination=" + deliveryLocation +
                ", distance=" + distance +
                ", timestamp=" + timestamp +
                '}';
    }
}
