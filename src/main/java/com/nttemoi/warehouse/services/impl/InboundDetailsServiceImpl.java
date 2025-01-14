package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.InboundDetails;
import com.nttemoi.warehouse.entities.StockBalance;
import com.nttemoi.warehouse.repositories.InboundDetailsRepository;
import com.nttemoi.warehouse.services.InboundDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InboundDetailsServiceImpl implements InboundDetailsService {

    private final InboundDetailsRepository inboundDetailsRepository;
    private final StockBalanceServiceImpl stockBalanceServiceImpl;

    public InboundDetailsServiceImpl (InboundDetailsRepository inboundDetailsRepository, StockBalanceServiceImpl stockBalanceServiceImpl) {
        this.inboundDetailsRepository = inboundDetailsRepository;
        this.stockBalanceServiceImpl = stockBalanceServiceImpl;
    }

    @Override
    public List <InboundDetails> findAll () {
        return inboundDetailsRepository.findAll();
    }

    @Override
    public InboundDetails findById (Long id) {
        return inboundDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("InboundDetails not found"));
    }

    @Override
    public void save (InboundDetails inboundDetails) {
        StockBalance stockBalance = new StockBalance();
        Optional <StockBalance> optionalStockBalance = Optional.ofNullable(stockBalanceServiceImpl.findByProductIdAndWarehouseId(inboundDetails.getProduct().getId(), inboundDetails.getWarehouse().getId()));
        if (optionalStockBalance.isPresent()) {
            stockBalance = optionalStockBalance.get();
            stockBalance.setMaxQuantity(Math.max(inboundDetails.getQuantity(), stockBalance.getMaxQuantity()));
            stockBalance.setTotalQuantity(stockBalance.getTotalQuantity() + inboundDetails.getQuantity());
        }
        else {
            stockBalance.setMaxQuantity(inboundDetails.getQuantity());
            stockBalance.setProduct(inboundDetails.getProduct());
            stockBalance.setWarehouse(inboundDetails.getWarehouse());
            stockBalance.setTotalQuantity(inboundDetails.getQuantity());
        }
        stockBalanceServiceImpl.save(stockBalance, true);
        inboundDetailsRepository.save(inboundDetails);
    }

    @Override
    public void deleteById (Long id) {
        InboundDetails inboundDetails = findById(id);
        StockBalance stockBalance = stockBalanceServiceImpl.findByProductIdAndWarehouseId(inboundDetails.getProduct().getId(), inboundDetails.getWarehouse().getId());
        stockBalance.setTotalQuantity(stockBalance.getTotalQuantity() - inboundDetails.getQuantity());
        stockBalanceServiceImpl.save(stockBalance, true);
        stockBalanceServiceImpl.updateWarehouseCapacity(inboundDetails.getWarehouse(), stockBalance);
        inboundDetailsRepository.delete(inboundDetails);
    }

    @Override
    public Page <InboundDetails> findAll (Long inboundId, int page, int size, String order, String orderBy) {
        return inboundDetailsRepository.findAllByInboundId(inboundId, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), orderBy)));
    }

    @Override
    public Page <InboundDetails> findAllByKeyword (Long inboundId, String keyword, int page, int size, String order, String orderBy) {
        return null;
    }
}
