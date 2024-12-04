package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Long> {
}
