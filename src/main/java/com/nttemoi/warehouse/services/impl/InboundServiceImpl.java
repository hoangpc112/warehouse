package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Inbound;
import com.nttemoi.warehouse.entities.InboundDetails;
import com.nttemoi.warehouse.repositories.InboundRepository;
import com.nttemoi.warehouse.services.InboundService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InboundServiceImpl implements InboundService {

    private final InboundRepository inboundRepository;

    public InboundServiceImpl (InboundRepository inboundRepository) {
        this.inboundRepository = inboundRepository;
    }

    @Override
    public List <Inbound> findAll () {
        return inboundRepository.findAll();
    }

    @Override
    public Inbound findById (Long id) {
        return inboundRepository.findById(id).orElseThrow(() -> new RuntimeException("Inbound not found"));
    }

    @Override
    public void save (Inbound inbound) {
        calculateTotalQuantity(inbound);
        inboundRepository.save(inbound);
    }

    private void calculateTotalQuantity (Inbound inbound) {
        Optional.ofNullable(inbound.getInboundDetails())
                .ifPresent(details -> {
                    long totalQuantity = details.stream()
                            .mapToLong(InboundDetails::getQuantity)
                            .sum();
                    long totalDamaged = details.stream()
                            .mapToLong(InboundDetails::getDamaged)
                            .sum();
                    inbound.setTotalQuantity(totalQuantity);
                    inbound.setTotalDamaged(totalDamaged);
                });
    }

    @Override
    public void deleteById (Long id) {
        inboundRepository.delete(findById(id));
    }

    @Override
    public Page <Inbound> findAll (int page, int size, String order, String orderBy) {
        return inboundRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), orderBy)));
    }


    @Override
    public Page <Inbound> findAllByKeyword (String keyword, int page, int size, String order, String orderBy) {
        return null;
    }
}
