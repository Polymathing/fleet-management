package com.example.fleet_management.dao;

import com.example.fleet_management.dao.entity.DeliveryOrderRow;
import com.example.fleet_management.dao.entity.LocationRow;
import com.example.fleet_management.dao.repository.LocationRepository;
import com.example.fleet_management.domain.Location;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LocationDAO {
    private final LocationRepository locationRepository;

    public LocationDAO(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Transactional(readOnly = true)
    public Set<Location> findAll() {

        return locationRepository.findAll()
                .stream()
                .map(LocationRow::toLocation)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Optional<Location> findById(Long id) {


        return locationRepository.findById(id)
                .map(LocationRow::toLocation);
    }

    @Transactional
    public Location save(Location location) {

        final var locationRow = LocationRow.toLocationRow(location);
        final var dbRecord = locationRepository.save(locationRow);

        return dbRecord.toLocation();
    }

    @Transactional
    public Optional<Location> update(Location location) {

        return locationRepository.findById(location.getLocationId())
                .map(dbRecord -> {

                    final var deliveryOrderRows = location.getDeliveryOrderSet()
                            .stream()
                            .map(DeliveryOrderRow::toDeliveryOrderRow)
                            .collect(Collectors.toSet());

                    dbRecord.setName(location.getName());
                    dbRecord.setLatitude(location.getLatitude());
                    dbRecord.setLongitude(location.getLongitude());
                    dbRecord.setDeliveryOrderRows(deliveryOrderRows);

                    return dbRecord;
                }).map(LocationRow::toLocation);
    }

    @Transactional
    public boolean delete(Long id) {

        return locationRepository.findById(id)
                .map(dbRecord -> {

                    locationRepository.delete(dbRecord);
                    return true;
                }).orElse(false);
    }
}
