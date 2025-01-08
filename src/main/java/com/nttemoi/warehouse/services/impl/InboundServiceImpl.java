package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Inbound;
import com.nttemoi.warehouse.repositories.InboundRepository;
import com.nttemoi.warehouse.services.InboundService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class InboundServiceImpl implements InboundService {

    private final InboundRepository inboundRepository;

    public InboundServiceImpl (InboundRepository inboundRepository) {
        this.inboundRepository = inboundRepository;
    }

    @Override
    public Inbound findById (Long id) {
        return inboundRepository.findById(id).orElseThrow(() -> new RuntimeException("Inbound not found"));
    }

    @Override
    public void save (Inbound inbound) {
        inboundRepository.save(inbound);
    }

    @Override
    public void deleteById (Long id) {
        inboundRepository.delete(findById(id));
    }

    @Override
    public void updateStatus (Long id, String status) {
        Inbound inbound = findById(id);
        inbound.setStatus(status);
        inboundRepository.save(inbound);
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
