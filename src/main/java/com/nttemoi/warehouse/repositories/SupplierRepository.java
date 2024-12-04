package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository <Supplier, Long> {
}
