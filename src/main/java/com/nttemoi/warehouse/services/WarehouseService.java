package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Warehouse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WarehouseService {
    List<Warehouse> findAll();

    Page<Warehouse> findAll(int page, int size);

    Warehouse findById(Long id);

    void save(Warehouse warehouse);

    void deleteById(Long id);

    Page<Warehouse> findByAddress(String keyword, int page, int size);

    void updateWarehouseStatus(Long id, boolean status);

    Page<Warehouse> findAllAndSort(int page, int size, String order, String orderBy);

    Page<Warehouse> findByKeywordAndSort(String keyword, int page, int size, String order, String orderBy);


    Page<Warehouse> findByCapacityIsGreaterThanEqual(Long keyword, int page, int size);

    Page<Warehouse> findByCapacityIsLessThanEqual(Long keyword, int page, int size);

    Page<Warehouse> findByCapacityIsBetween(Long minCapacity, Long maxCapacity, int page, int size);
}
