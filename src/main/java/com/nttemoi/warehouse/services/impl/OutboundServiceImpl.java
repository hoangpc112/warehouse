package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Outbound;
import com.nttemoi.warehouse.entities.User;
import com.nttemoi.warehouse.repositories.OutboundRepository;
import com.nttemoi.warehouse.services.OutboundService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
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
        outboundRepository.save(outbound);
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
    public Page <Outbound> findAllByUser (User user, int page, int size, String order, String orderBy) {
        return outboundRepository.findAllByUser(user, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), orderBy)));
    }
}
