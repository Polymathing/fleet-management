package com.example.fleet_management;

import com.example.fleet_management.dao.entity.TruckRow;
import com.example.fleet_management.dao.repository.TruckRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/truck")
public class TruckController {

    private final TruckRepository truckRepository;


    public TruckController(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll() {

        final var trucks = new HashSet<TruckRow>(truckRepository.findAll());

        return ResponseEntity.ok(trucks);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TruckRow truckRow) {

        try {

            final var truck = truckRepository.save(truckRow);
            return ResponseEntity.ok(truck);
        }
        catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}
