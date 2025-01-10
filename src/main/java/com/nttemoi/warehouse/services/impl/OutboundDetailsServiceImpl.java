package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.OutboundDetails;
import com.nttemoi.warehouse.repositories.OutboundDetailsRepository;
import com.nttemoi.warehouse.services.OutboundDetailsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        return outboundDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("OutboundDetails not found"));
    }

    @Override
    public void save (OutboundDetails outboundDetails) {
        outboundDetailsRepository.save(outboundDetails);
    }

    @Override
    public void deleteById (Long id) {
        outboundDetailsRepository.delete(findById(id));
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
