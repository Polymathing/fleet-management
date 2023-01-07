package com.example.fleet_management.dao;

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
    private final LocationRepository repository;

    public LocationDAO(LocationRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Set<Location> findAll() {

        return repository.findAll()
                .stream()
                .map(LocationRow::toLocation)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Optional<Location> findById(Long id) {


        return repository.findById(id)
                .map(LocationRow::toLocation);
    }

    @Transactional(readOnly = true)
    public Optional<Location> findByName(String name) {

        return repository.findByName(name)
                .map(LocationRow::toLocation);
    }

    @Transactional
    public Location save(Location location) {

        final var locationRow = LocationRow.toLocationRow(location);
        final var dbRecord = repository.save(locationRow);

        return dbRecord.toLocation();
    }

    @Transactional
    public Optional<Location> update(Long id, Location location) {

        return repository.findById(id)
                .map(dbRecord -> {

                    dbRecord.setName(location.name());
                    dbRecord.setLatitude(location.latitude());
                    dbRecord.setLongitude(location.longitude());

                    return dbRecord;
                }).map(LocationRow::toLocation);
    }

    @Transactional
    public boolean deleteById(Long id) {

        return repository.findById(id)
                .map(dbRecord -> {

                    repository.delete(dbRecord);
                    return true;
                }).orElse(false);
    }
}
