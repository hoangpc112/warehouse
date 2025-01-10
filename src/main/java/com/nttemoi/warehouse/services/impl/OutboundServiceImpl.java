package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Outbound;
import com.nttemoi.warehouse.entities.OutboundDetails;
import com.nttemoi.warehouse.repositories.OutboundRepository;
import com.nttemoi.warehouse.services.OutboundService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutboundServiceImpl implements OutboundService {

    private final OutboundRepository outboundRepository;

    public OutboundServiceImpl (OutboundRepository outboundRepository) {
        this.outboundRepository = outboundRepository;
    }

    @Override
    public List <Outbound> findAll () {
        return outboundRepository.findAll();
    }

    @Override
    public Outbound findById (Long id) {
        return outboundRepository.findById(id).orElseThrow(() -> new RuntimeException("Outbound not found"));
    }

    @Override
    public void save (Outbound outbound) {
        calculateTotalQuantity(outbound);
        outboundRepository.save(outbound);
    }

    private void calculateTotalQuantity (Outbound outbound) {
        Optional.ofNullable(outbound.getOutboundDetails())
                .ifPresent(details -> {
                    long totalQuantity = details.stream()
                            .mapToLong(OutboundDetails::getQuantity)
                            .sum();
                    outbound.setTotalQuantity(totalQuantity);
                });
    }

    @Override
    public void deleteById (Long id) {
        outboundRepository.delete(findById(id));
    }

    @Override
    public Page <Outbound> findAll (int page, int size, String order, String orderBy) {
        return outboundRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), orderBy)));
    }


    @Override
    public Page <Outbound> findAllByKeyword (String keyword, int page, int size, String order, String orderBy) {
        return null;
    }
}
