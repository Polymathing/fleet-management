package com.example.fleet_management.domain.service;

import com.example.fleet_management.dao.LocationDAO;
import com.example.fleet_management.domain.Location;
import com.example.fleet_management.domain.validator.LocationLatitudeValidator;
import com.example.fleet_management.domain.validator.LocationLongitudeValidator;
import com.example.fleet_management.exception.ExistingRecordException;
import com.example.fleet_management.exception.location.InvalidLatitudeException;
import com.example.fleet_management.exception.location.InvalidLongitudeException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class LocationService {
    private final LocationLatitudeValidator latitudeValidator;
    private final LocationLongitudeValidator longitudeValidator;
    private final LocationDAO dao;

    public LocationService(LocationDAO dao, LocationLatitudeValidator latitudeValidator, LocationLongitudeValidator longitudeValidator) {
        this.dao = dao;
        this.latitudeValidator = latitudeValidator;
        this.longitudeValidator = longitudeValidator;
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

        final var locationIdExists = dao.findById(location.getId()).isPresent();
        final var locationNameExists = dao.findByName(location.getName()).isPresent();
        final var latitudeIsValid = latitudeValidator.isLatitudeValid(location.getLatitude().toString());
        final var longitudeIsValid = longitudeValidator.isLatitudeValid(location.getLongitude().toString());

        if(locationIdExists) {
            throw new ExistingRecordException("location", "ID");
        }
        else if(locationNameExists) {
            throw new ExistingRecordException("location", "name");
        }
        else if(!latitudeIsValid) {
            throw new InvalidLatitudeException(location.getLatitude().toString());
        }
        else if(!longitudeIsValid) {
            throw new InvalidLongitudeException(location.getLongitude().toString());
        }
    }

    public Optional<Location> update(Long id, Location location) {

        return dao.update(id, location);
    }

    public boolean delete(Long id) {

        return dao.deleteById(id);
    }
}
