package com.example.fleet_management.dao;

import com.example.fleet_management.dao.entity.DeliveryOrderRow;
import com.example.fleet_management.dao.entity.LocationRow;
import com.example.fleet_management.dao.entity.TruckRow;
import com.example.fleet_management.dao.repository.DeliveryOrderRepository;
import com.example.fleet_management.domain.DeliveryOrder;
import com.example.fleet_management.domain.Location;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DeliveryOrderDAO {

    private final DeliveryOrderRepository deliveryOrderRepository;

    public DeliveryOrderDAO(DeliveryOrderRepository deliveryOrderRepository) {
        this.deliveryOrderRepository = deliveryOrderRepository;
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
    public DeliveryOrder save(DeliveryOrder deliveryOrder) {

        final var deliveryOrderRow = DeliveryOrderRow.toDeliveryOrderRow(deliveryOrder);
        final var dbRecord = deliveryOrderRepository.save(deliveryOrderRow);

        return dbRecord.toDeliveryOrder();
    }

    @Transactional
    public Optional<DeliveryOrder> update(DeliveryOrder deliveryOrder) {

        return deliveryOrderRepository.findById(deliveryOrder.getId())
                .map(dbRecord -> {

                    final var truckRow = TruckRow.toTruckRow(deliveryOrder.getTruck());
                    final var originLocationRow = LocationRow.toLocationRow(deliveryOrder.getOriginLocation());
                    final var deliveryLocationRow = LocationRow.toLocationRow(deliveryOrder.getDeliveryLocation());

                    dbRecord.setTruckRow(truckRow);
                    dbRecord.setDeliveryLocation(deliveryLocationRow);
                    dbRecord.setOriginLocation(originLocationRow);
                    dbRecord.setDistance(deliveryOrder.getDistance());

                    return dbRecord;
                }).map(DeliveryOrderRow::toDeliveryOrder);
    }

    @Transactional
    public boolean delete(Long id) {

        return deliveryOrderRepository.findById(id)
                .map(dbRecord -> {

                    deliveryOrderRepository.delete(dbRecord);
                    return true;
                }).orElse(false);
    }
}
