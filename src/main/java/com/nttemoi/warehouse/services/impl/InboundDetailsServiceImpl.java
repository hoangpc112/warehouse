package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.InboundDetails;
import com.nttemoi.warehouse.repositories.InboundDetailsRepository;
import com.nttemoi.warehouse.services.InboundDetailsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class InboundDetailsServiceImpl implements InboundDetailsService {

    private final InboundDetailsRepository inboundDetailsRepository;

    public InboundDetailsServiceImpl (InboundDetailsRepository inboundDetailsRepository) {
        this.inboundDetailsRepository = inboundDetailsRepository;
    }

    @Override
    public InboundDetails findById (Long id) {
        return inboundDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("InboundDetails not found"));
    }

    @Override
    public void save (InboundDetails inboundDetails) {
        inboundDetailsRepository.save(inboundDetails);
    }

    @Override
    public void deleteById (Long id) {
        inboundDetailsRepository.delete(findById(id));
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
