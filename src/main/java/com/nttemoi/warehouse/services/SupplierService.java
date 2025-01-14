package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Supplier;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SupplierService {
    List <Supplier> findAll ();

    Supplier findById (Long id);

    void save (Supplier supplier);

    void deleteById (Long id);

    Page <Supplier> findAll (int page, int size, String order, String orderBy);

    Page <Supplier> findAllByKeyword (String keyword, int page, int size, String order, String orderBy);

    Supplier findByName (String name);
}