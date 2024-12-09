package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Outbound;
import com.nttemoi.warehouse.repositories.OutboundRepository;
import com.nttemoi.warehouse.services.OutboundService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return outboundRepository.findById(id).orElse(null);
    }

    @Override
    public void save (Outbound outbound) {
        outboundRepository.save(outbound);
    }

    @Override
    public void deleteById (Long id) {
        outboundRepository.deleteById(id);
    }
}
