package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Outbound;
import com.nttemoi.warehouse.repositories.OutboundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutboundService {

    @Autowired
    private OutboundRepository outboundRepository;

    public List <Outbound> findAll () {
        return outboundRepository.findAll();
    }

    public Outbound findById (long id) {
        return outboundRepository.findById(id).orElse(null);
    }

    public void save (Outbound outbound) {
        outboundRepository.save(outbound);
    }

    public void deleteById (Long id) {
        outboundRepository.deleteById(id);
    }
}
