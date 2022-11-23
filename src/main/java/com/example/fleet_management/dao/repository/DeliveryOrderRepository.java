package com.example.fleet_management.dao.repository;

import com.example.fleet_management.dao.entity.DeliveryOrderRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrderRow, Long> {
}
