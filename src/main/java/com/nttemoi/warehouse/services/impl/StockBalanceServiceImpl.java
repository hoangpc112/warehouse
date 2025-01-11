package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.StockBalance;
import com.nttemoi.warehouse.repositories.StockBalanceRepository;
import com.nttemoi.warehouse.services.StockBalanceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StockBalanceServiceImpl implements StockBalanceService {

    @Autowired
    private StockBalanceRepository stockBalanceRepository;

    @Override
    public List<StockBalance> findAll() {
        return stockBalanceRepository.findAll();
    }

    @Override
    public Page<StockBalance> findAll(int page, int size) {
        return stockBalanceRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    @Override
    public StockBalance findById(Long id) {
        return stockBalanceRepository.findById(id).orElse(null);
    }

    @Override
    public void save(StockBalance stockBalance) {
        stockBalanceRepository.save(stockBalance);
    }

    @Override
    public void deleteById(Long id) {
        stockBalanceRepository.deleteById(id);
    }

    @Override
    public Page<StockBalance> findAllAndSort(int page, int size, String order, String orderBy) {
        if (order.equals("asc")) {
            return stockBalanceRepository.findAll(PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        } else {
            return stockBalanceRepository.findAll(PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }

    }

    @Override
    public Page<StockBalance> findByProductId(Long productId, int page, int size) {
        return stockBalanceRepository.findStockBalanceByProductId(productId, PageRequest.of(page, size));
    }

    @Override
    public Page<StockBalance> findByWarehouseId(Long warehouseId, int page, int size) {
        return stockBalanceRepository.findStockBalanceByWarehouseId(warehouseId, PageRequest.of(page, size, Sort.by("id")));
    }

    @Override
    public Page<StockBalance> findByWarehouseIdAndSort(Long warehouseId, int page, int size, String order, String orderBy) {
        if (order.equals("asc")) {
            return stockBalanceRepository.findStockBalanceByWarehouseId(warehouseId, PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        } else {
            return stockBalanceRepository.findStockBalanceByWarehouseId(warehouseId, PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }
    }

    @Override
    public void updateStockBalanceStatus(Long id, boolean status) {
        stockBalanceRepository.updateStockBalanceStatus(id, status);
    }

    public List<StockBalance> findByWarehouseId(Long warehouseId) {
        return stockBalanceRepository.findStockBalanceByWarehouseId(warehouseId);
    }
}
