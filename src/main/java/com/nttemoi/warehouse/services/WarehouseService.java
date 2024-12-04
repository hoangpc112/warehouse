package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Warehouse;
import com.nttemoi.warehouse.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public List <Warehouse> findAll () {
        return warehouseRepository.findAll();
    }

    public Warehouse findById (long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    public void save (Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    public void deleteById (Long id) {
        warehouseRepository.deleteById(id);
    }
}
