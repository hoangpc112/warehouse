package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.User;
import com.nttemoi.warehouse.entities.Warehouse;
import com.nttemoi.warehouse.repositories.WarehouseRepository;
import com.nttemoi.warehouse.services.WarehouseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    @Override
    public Page<Warehouse> findAll(int page, int size) {
        return warehouseRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    @Override
    public Warehouse findById(Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    @Override
    public void deleteById(Long id) {

        Warehouse warehouse = warehouseRepository.findById(id).orElse(null);
        if (warehouse != null) {
            for (User user : warehouse.getUsers()) {
                user.setWarehouse(null);
            }
            warehouseRepository.deleteById(id);
        }


    }

    @Override
    public Page<Warehouse> findByAddress(String keyword, int page, int size) {
        return warehouseRepository.findByAddressContainingIgnoreCase(keyword, PageRequest.of(page, size, Sort.by("address")));
    }

    public Warehouse findByAddress(String keyword) {
        return warehouseRepository.findByAddress(keyword).orElse(null);
    }

    @Override
    public void updateWarehouseStatus(Long id, boolean status) {
        warehouseRepository.updateWarehouseStatus(id, status);
    }

    @Override
    public Page<Warehouse> findAllAndSort(int page, int size, String order, String orderBy) {

        if (order.equals("asc")) {

            return warehouseRepository.findAll(PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        } else {
            return warehouseRepository.findAll(PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }
    }

    @Override
    public Page<Warehouse> findByKeywordAndSort(String keyword, int page, int size, String order, String orderBy) {
        if (order.equals("asc")) {
            return warehouseRepository.findByAddressContainingIgnoreCase(keyword, PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        } else {
            return warehouseRepository.findByAddressContainingIgnoreCase(keyword, PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }
    }

    @Override
    public Page<Warehouse> findByCapacityIsGreaterThanEqual(Long keyword, int page, int size) {
        return warehouseRepository.findByCapacityIsGreaterThanEqual(keyword, PageRequest.of(page, size));
    }

    @Override
    public Page<Warehouse> findByCapacityIsLessThanEqual(Long keyword, int page, int size) {
        return warehouseRepository.findByCapacityIsLessThanEqual(keyword, PageRequest.of(page, size));
    }

    @Override
    public Page<Warehouse> findByCapacityIsBetween(Long minCapacity, Long maxCapacity, int page, int size) {
        return warehouseRepository.findByCapacityIsBetween(minCapacity, maxCapacity, PageRequest.of(page, size));
    }


}
