package com.example.fleet_management.domain.service;

import com.example.fleet_management.dao.LocationDAO;
import com.example.fleet_management.domain.Location;
import com.example.fleet_management.exception.ExistingRecordException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class LocationService {
    private final LocationDAO dao;

    public LocationService(LocationDAO dao) {
        this.dao = dao;
    }

    public Set<Location> findAll() {

        return dao.findAll();
    }

    public Optional<Location> findById(Long id) {

        return dao.findById(id);
    }

    public Location save(Location location) {

        final var opTruck = dao.findById(location.getId());

        if(opTruck.isPresent()) {
            throw new ExistingRecordException("location");
        }

        return dao.save(location);
    }

    public Optional<Location> update(Long id, Location location) {

        return dao.update(id, location);
    }

    public boolean delete(Long id) {

        return dao.deleteById(id);
    }
}
