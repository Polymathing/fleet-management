package com.example.fleet_management.domain.service;

import com.example.fleet_management.dao.DeliveryOrderDAO;
import com.example.fleet_management.domain.DeliveryOrder;
import com.example.fleet_management.exception.ExistingRecordException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class DeliveryOrderService {

    private final DeliveryOrderDAO dao;

    public DeliveryOrderService(DeliveryOrderDAO dao) {
        this.dao = dao;
    }

    public Set<DeliveryOrder> findAll() { return dao.findAll(); }

    public Optional<DeliveryOrder> findById(Long id) {

        return dao.findById(id);
    }

    public DeliveryOrder save(DeliveryOrder deliveryOrder) {

        final var opDeliveryOrder = dao.findById(deliveryOrder.id());

        if(opDeliveryOrder.isPresent()) {
            throw new ExistingRecordException("deliveryOrder");
        }

        return dao.save(deliveryOrder);
    }

    public Optional<DeliveryOrder> update(DeliveryOrder deliveryOrder) {

        return dao.update(deliveryOrder);
    }

    public boolean deleteById(Long id) {

        return dao.deleteById(id);
    }
}
