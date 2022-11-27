package com.example.fleet_management.domain.service;

import com.example.fleet_management.dao.TruckDAO;
import com.example.fleet_management.domain.Truck;
import com.example.fleet_management.exception.truck.ExistingLicensePlateException;
import com.example.fleet_management.exception.ExistingRecordException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class TruckService {

    private final TruckDAO dao;

    public TruckService(TruckDAO dao) {
        this.dao = dao;
    }

    public Set<Truck> findAll() {

        return dao.findAll();
    }

    public Optional<Truck> findById(Long id) {

        return dao.findById(id);
    }

    public Truck save(Truck truck) {

        validateNewTruck(truck);

        return dao.save(truck);
    }

    private void validateNewTruck(Truck truck) {

        final var opTruckById = dao.findById(truck.getId());
        final var opTruckByLicensePlate = dao.findByLicensePlate(truck.getLicensePlate());

        if(opTruckById.isPresent()) {
            throw new ExistingRecordException("truck", "ID");
        }
        else if(opTruckByLicensePlate.isPresent()) {
            throw new ExistingLicensePlateException(truck.getLicensePlate());
        }
    }

    public Optional<Truck> update(Long id, Truck truck) {

        return dao.update(id, truck);
    }

    public boolean delete(Long id) {

        return dao.deleteById(id);
    }
}
