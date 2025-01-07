package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.StockBalance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockBalanceRepository extends JpaRepository<StockBalance, Long> {
    StockBalance findStockBalanceById(long id);

    Page<StockBalance> findStockBalanceByProductId(long productId, Pageable pageable);

    Page<StockBalance> findStockBalanceByProductName(String productName, Pageable pageable);

    Page<StockBalance> findStockBalanceByWarehouseId(long warehouseId, Pageable pageable);

    List<StockBalance> findStockBalanceByProductName(String name);

    List<StockBalance> findStockBalanceByWarehouseId(long warehouseId);

    StockBalance findStockBalanceByProductIdAndWarehouseId(long productId, long warehouseId);


}
