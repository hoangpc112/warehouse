package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.OutboundDetails;
import com.nttemoi.warehouse.entities.StockBalance;
import com.nttemoi.warehouse.repositories.OutboundDetailsRepository;
import com.nttemoi.warehouse.services.OutboundDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OutboundDetailsServiceImpl implements OutboundDetailsService {

    private final OutboundDetailsRepository outboundDetailsRepository;
    private final StockBalanceServiceImpl stockBalanceServiceImpl;

    public OutboundDetailsServiceImpl (OutboundDetailsRepository outboundDetailsRepository, StockBalanceServiceImpl stockBalanceServiceImpl) {
        this.outboundDetailsRepository = outboundDetailsRepository;
        this.stockBalanceServiceImpl = stockBalanceServiceImpl;
    }

    @Override
    public List <OutboundDetails> findAll () {
        return outboundDetailsRepository.findAll();
    }

    @Override
    public OutboundDetails findById (Long id) {
        return outboundDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("OutboundDetails not found"));
    }

    @Override
    public void save (OutboundDetails outboundDetails) {
        StockBalance stockBalance = Optional.ofNullable(stockBalanceServiceImpl.findByProductIdAndWarehouseId(outboundDetails.getProduct().getId(), outboundDetails.getWarehouse().getId()))
                .orElseThrow(() -> new RuntimeException(outboundDetails.getProduct().getName() + " is not available in warehouse: " + outboundDetails.getWarehouse().getAddress()));
        if (stockBalance.getTotalQuantity() < outboundDetails.getQuantity()) {
            throw new RuntimeException("Stock is not enough");
        }
        stockBalance.setTotalQuantity(stockBalance.getTotalQuantity() - outboundDetails.getQuantity());
        stockBalanceServiceImpl.save(stockBalance, false);
        outboundDetailsRepository.save(outboundDetails);
    }

    @Override
    public void deleteById (Long id) {
        OutboundDetails outboundDetails = findById(id);
        StockBalance stockBalance = stockBalanceServiceImpl.findByProductIdAndWarehouseId(outboundDetails.getProduct().getId(), outboundDetails.getWarehouse().getId());
        stockBalance.setTotalQuantity(stockBalance.getTotalQuantity() + outboundDetails.getQuantity());
        stockBalanceServiceImpl.save(stockBalance, false);
        stockBalanceServiceImpl.updateWarehouseCapacity(outboundDetails.getWarehouse(), stockBalance);
        outboundDetailsRepository.delete(outboundDetails);
    }

    @Override
    public Page <OutboundDetails> findAll (Long outboundId, int page, int size, String order, String orderBy) {
        return outboundDetailsRepository.findByOutboundId(outboundId, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), orderBy)));
    }

    @Override
    public Page <OutboundDetails> findAllByKeyword (Long outboundId, String keyword, int page, int size, String order, String orderBy) {
        return null;
    }
}
