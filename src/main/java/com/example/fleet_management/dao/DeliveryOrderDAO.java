package com.example.fleet_management.dao;

import com.example.fleet_management.dao.entity.DeliveryOrderRow;
import com.example.fleet_management.dao.repository.DeliveryOrderRepository;
import com.example.fleet_management.dao.repository.LocationRepository;
import com.example.fleet_management.dao.repository.TruckRepository;
import com.example.fleet_management.domain.DeliveryOrder;
import com.example.fleet_management.exception.RecordNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DeliveryOrderDAO {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final LocationRepository locationRepository;
    private final TruckRepository truckRepository;

    public DeliveryOrderDAO(DeliveryOrderRepository deliveryOrderRepository, LocationRepository locationRepository, TruckRepository truckRepository) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.locationRepository = locationRepository;
        this.truckRepository = truckRepository;
    }

    @Transactional(readOnly = true)
    public Set<DeliveryOrder> findAll() {

        return deliveryOrderRepository
                .findAll()
                .stream()
                .map(DeliveryOrderRow::toDeliveryOrder)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Optional<DeliveryOrder> findById(Long id) {

        return deliveryOrderRepository
                .findById(id)
                .map(DeliveryOrderRow::toDeliveryOrder);
    }

    @Transactional
    public DeliveryOrder save(String licensePlate, Long originId, Long destinationId) {

        final var deliveryOrderRow = populateDeliveryOrder(licensePlate, originId, destinationId);

        final var dbRecord = deliveryOrderRepository.save(deliveryOrderRow);

        return dbRecord.toDeliveryOrder();
    }

    private DeliveryOrderRow populateDeliveryOrder(String licensePlate, Long originId, Long destinationId) {

        final var truck = truckRepository.findByLicensePlate(licensePlate)
                .orElseThrow(()-> new RecordNotFoundException("truck", "license plate"));

        final var origin = locationRepository.findById(originId)
                .orElseThrow(() -> new RecordNotFoundException("origin", "ID"));

        final var destination = locationRepository.findById(destinationId)
                .orElseThrow(() -> new RecordNotFoundException("destination", "ID"));

        final var deliveryOrderRow = new DeliveryOrderRow();

        deliveryOrderRow.setTruckRow(truck);
        deliveryOrderRow.setOrigin(origin);
        deliveryOrderRow.setDestination(destination);

        final var distance = calculateDistanceInKilometers(
                origin.getLatitude(), origin.getLongitude(),
                destination.getLatitude(), destination.getLongitude());

        deliveryOrderRow.setDistance(distance.doubleValue());

        truck.addDeliveryOrderRecord(deliveryOrderRow);
        origin.addDeliveryOrderOriginRow(deliveryOrderRow);
        destination.addDeliveryOrderDestinationRow(deliveryOrderRow);

        return deliveryOrderRow;
    }

    private static BigDecimal calculateDistanceInKilometers(BigDecimal lat1, BigDecimal lon1,
                                                            BigDecimal lat2, BigDecimal lon2) {

        if ((Objects.equals(lat1, lat2)) && (Objects.equals(lon1, lon2))) {
            return BigDecimal.valueOf(0);
        }

        BigDecimal theta = lon1.subtract(lon2);

        BigDecimal dist = BigDecimal.valueOf(Math.sin(Math.toRadians((lat1.doubleValue())))
                * Math.sin(Math.toRadians(lat2.doubleValue()))
                + Math.cos(Math.toRadians(lat1.doubleValue()))
                * Math.cos(Math.toRadians(lat2.doubleValue()))
                * Math.cos(Math.toRadians(theta.doubleValue())));

        dist = BigDecimal.valueOf(Math.acos(dist.doubleValue()));
        dist = BigDecimal.valueOf(Math.toDegrees(dist.doubleValue()));
        dist = dist.multiply(BigDecimal.valueOf(60)).multiply(BigDecimal.valueOf(1.1515));

        return  dist.multiply(BigDecimal.valueOf(1.609344));
    }

    @Transactional
    public boolean deleteById(Long id) {

        return deliveryOrderRepository.findById(id)
                .map(dbRecord -> {

                    deliveryOrderRepository.delete(dbRecord);

                    return true;
                }).orElse(false);
    }
}
