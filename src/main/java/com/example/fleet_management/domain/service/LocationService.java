package com.example.fleet_management.domain.service;

import com.example.fleet_management.dao.LocationDAO;
import com.example.fleet_management.domain.Location;
import com.example.fleet_management.domain.validator.LocationGeoCoordinateValidator;
import com.example.fleet_management.exception.ExistingRecordException;
import com.example.fleet_management.exception.location.InvalidCoordinateException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class LocationService {
    private final LocationGeoCoordinateValidator coordinateValidator;
    private final LocationDAO dao;

    public LocationService(LocationDAO dao, LocationGeoCoordinateValidator coordinateValidator) {
        this.dao = dao;
        this.coordinateValidator = coordinateValidator;
    }

    public Set<Location> findAll() {

        return dao.findAll();
    }

    public Optional<Location> findById(Long id) {

        return dao.findById(id);
    }

    public Location save(Location location) {

        validateNewLocation(location);

        return dao.save(location);
    }

    private void validateNewLocation(Location location) {

        final var locationIdExists = dao.findById(location.id()).isPresent();
        final var locationNameExists = dao.findByName(location.name()).isPresent();
        final var latitudeIsValid = coordinateValidator.isCoordinateValid(location.latitude().toString());
        final var longitudeIsValid = coordinateValidator.isCoordinateValid(location.longitude().toString());

        if(locationIdExists) {
            throw new ExistingRecordException("location", "ID");
        }
        else if(locationNameExists) {
            throw new ExistingRecordException("location", "name");
        }
        else if(!latitudeIsValid) {
            throw new InvalidCoordinateException("Latitude", location.latitude().toString());
        }
        else if(!longitudeIsValid) {
            throw new InvalidCoordinateException("Longitude", location.longitude().toString());
        }
    }

    public Optional<Location> update(Long id, Location location) {

        return dao.update(id, location);
    }

    public boolean delete(Long id) {

        return dao.deleteById(id);
    }
}
