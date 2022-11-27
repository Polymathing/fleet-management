package com.example.fleet_management.dao;

import com.example.fleet_management.dao.entity.DeliveryOrderRow;
import com.example.fleet_management.dao.entity.TruckRow;
import com.example.fleet_management.dao.repository.TruckRepository;
import com.example.fleet_management.domain.Truck;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TruckDAO {
    private final TruckRepository truckRepository;

    public TruckDAO(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }


    @Transactional(readOnly = true)
    public Set<Truck> findAll() {

        return truckRepository.findAll()
                .stream()
                .map(TruckRow::toTruck)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Optional<Truck> findById(Long id) {

        return truckRepository
                .findById(id)
                .map(TruckRow::toTruck);
    }

    @Transactional(readOnly = true)
    public Optional<Truck> findByLicensePlate(String licensePlate) {

        return truckRepository.findByLicensePlate(licensePlate)
                .map(TruckRow::toTruck);
    }

    @Transactional
    public Truck save(Truck truck) {

        final var truckRow = TruckRow.toTruckRow(truck);
        final var dbRecord =truckRepository.save(truckRow);

        return dbRecord.toTruck();
    }

    @Transactional
    public Optional<Truck> update(Long id, Truck truck) {

        return truckRepository.findById(id)
                .map(dbRecord -> {

                    dbRecord.setKilometersPerLiter(truck.getKilometersPerLiter());

                    return dbRecord;
                }).map(TruckRow::toTruck);
    }

    @Transactional
    public boolean deleteById(Long id) {

        return truckRepository.findById(id)
                .map(truckRow -> {

                    truckRepository.delete(truckRow);
                    return true;
                })
                .orElse( false);
    }

}
