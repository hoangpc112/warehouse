package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Warehouse;
import com.nttemoi.warehouse.repositories.WarehouseRepository;
import com.nttemoi.warehouse.services.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl (WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public List <Warehouse> findAll () {
        return warehouseRepository.findAll();
    }

    @Override
    public Warehouse findById (Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    @Override
    public void save (Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    @Override
    public void deleteById (Long id) {
        warehouseRepository.deleteById(id);
    }
}
