package com.example.fleet_management.domain.service;

import com.example.fleet_management.dao.DeliveryOrderDAO;
import com.example.fleet_management.domain.DeliveryOrder;
import com.example.fleet_management.exception.EqualValuesException;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

    public DeliveryOrder save(String licensePlate, Long originId, Long destinationId) {

        if(Objects.equals(originId, destinationId)) {
            throw new EqualValuesException("Origin", "Destination");
        }

        return dao.save(licensePlate, originId, destinationId);
    }


    public boolean delete(Long id) {

        return dao.deleteById(id);
    }
}
