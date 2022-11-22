package com.example.fleet_management.dao.repository;

import com.example.fleet_management.dao.entity.TruckRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TruckRepository extends JpaRepository <TruckRow, Long> {
}
