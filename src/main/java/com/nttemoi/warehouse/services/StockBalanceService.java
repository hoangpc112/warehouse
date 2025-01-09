package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.StockBalance;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StockBalanceService {
    List<StockBalance> findAll();

    Page<StockBalance> findAll(int page, int size);

    StockBalance findById(Long id);

    void save(StockBalance stockBalance);

    void deleteById(Long id);

    Page<StockBalance> findAllAndSort(int page, int size, String order, String orderBy);

    Page<StockBalance> findByProductId(Long productId, int page, int size);


    Page<StockBalance> findByWarehouseId(Long warehouseId, int page, int size);

    Page<StockBalance> findByWarehouseIdAndSort(Long warehouseId, int page, int size, String order, String orderBy);

}
