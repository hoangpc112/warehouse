package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Warehouse;

import java.util.List;

public interface WarehouseService {
    List <Warehouse> findAll ();

    Warehouse findById (Long id);

    void save (Warehouse warehouse);

    void deleteById (Long id);
}
