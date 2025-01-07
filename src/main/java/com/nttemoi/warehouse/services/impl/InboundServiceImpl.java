package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Inbound;
import com.nttemoi.warehouse.repositories.InboundRepository;
import com.nttemoi.warehouse.services.InboundService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return inboundRepository.findById(id).orElse(null);
    }

    @Override
    public void save (Inbound inbound) {
        inboundRepository.save(inbound);
    }

    @Override
    public void deleteById (Long id) {
        inboundRepository.deleteById(id);
    }
}
