package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Supplier;

import java.util.List;

public interface SupplierService {
    List <Supplier> findAll ();

    Supplier findById (Long id);

    void save (Supplier supplier);

    void deleteById (Long id);
}
