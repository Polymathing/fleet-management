package com.example.fleet_management.dao;

import com.example.fleet_management.dao.entity.DeliveryOrderRow;
import com.example.fleet_management.dao.entity.LocationRow;
import com.example.fleet_management.dao.entity.TruckRow;
import com.example.fleet_management.dao.repository.DeliveryOrderRepository;
import com.example.fleet_management.domain.DeliveryOrder;
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

        return deliveryOrderRepository.findById(deliveryOrder.id())
                .map(dbRecord -> {

                    final var truckRow = TruckRow.toTruckRow(deliveryOrder.truck());
                    final var originLocationRow = LocationRow.toLocationRow(deliveryOrder.origin());
                    final var deliveryLocationRow = LocationRow.toLocationRow(deliveryOrder.destination());

                    dbRecord.setTruckRow(truckRow);
                    dbRecord.setDestination(deliveryLocationRow);
                    dbRecord.setOrigin(originLocationRow);
                    dbRecord.setDistance(deliveryOrder.distance());

                    return dbRecord;
                }).map(DeliveryOrderRow::toDeliveryOrder);
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
