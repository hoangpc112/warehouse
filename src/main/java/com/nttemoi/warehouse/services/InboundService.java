package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Inbound;
import com.nttemoi.warehouse.repositories.InboundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InboundService {

    @Autowired
    private InboundRepository inboundRepository;

    public List <Inbound> findAll () {
        return inboundRepository.findAll();
    }

    public Inbound findById (Long id) {
        return inboundRepository.findById(id).orElse(null);
    }

    public void save (Inbound inbound) {
        inboundRepository.save(inbound);
    }

    public void deleteById (Long id) {
        inboundRepository.deleteById(id);
    }
}
