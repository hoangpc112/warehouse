package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.OutboundDetails;
import com.nttemoi.warehouse.repositories.OutboundDetailsRepository;
import com.nttemoi.warehouse.services.OutboundDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutboundDetailsServiceImpl implements OutboundDetailsService {

    private final OutboundDetailsRepository outboundDetailsRepository;

    public OutboundDetailsServiceImpl (OutboundDetailsRepository outboundDetailsRepository) {
        this.outboundDetailsRepository = outboundDetailsRepository;
    }

    @Override
    public List <OutboundDetails> findAll () {
        return outboundDetailsRepository.findAll();
    }

    @Override
    public OutboundDetails findById (Long id) {
        return outboundDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public void save (OutboundDetails outboundDetails) {
        outboundDetailsRepository.save(outboundDetails);
    }

    @Override
    public void deleteById (Long id) {
        outboundDetailsRepository.deleteById(id);
    }
}
