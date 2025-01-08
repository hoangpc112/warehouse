package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.OutboundDetails;

import java.util.List;

public interface OutboundDetailsService {
    List <OutboundDetails> findAll ();

    OutboundDetails findById (Long id);

    void save (OutboundDetails outboundDetails);

    void deleteById (Long id);
}
