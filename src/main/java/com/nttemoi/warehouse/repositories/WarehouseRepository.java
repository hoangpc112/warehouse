package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository <Warehouse, Long> {
    Optional <Warehouse> findByAddress (String address);

    Page <Warehouse> findByAddressContainingIgnoreCase (String address, Pageable pageable);

    // Page<Warehouse> findByAddressLike(String address, Pageable pageable);

    Page <Warehouse> findByCapacityIsGreaterThanEqual (long capacityIsGreaterThan, Pageable pageable);

    Page <Warehouse> findByCapacityIsLessThanEqual (long capacityIsLessThan, Pageable pageable);

    Page <Warehouse> findByCapacityIsBetween (long minCapacity, Long maxCapacity, Pageable pageable);

    @Query ("UPDATE Warehouse t SET t.active = :status WHERE t.id = :id")
    @Modifying
    void updateWarehouseStatus (Long id, boolean status);
}