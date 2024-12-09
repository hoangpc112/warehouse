package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Outbound;

import java.util.List;

public interface OutboundService {
    List <Outbound> findAll ();

    Outbound findById (Long id);

    void save (Outbound outbound);

    void deleteById (Long id);
}
