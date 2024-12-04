package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository <Warehouse, Long> {
}
