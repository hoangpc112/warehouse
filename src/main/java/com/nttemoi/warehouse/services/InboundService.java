package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Inbound;

import java.util.List;

public interface InboundService {
    List <Inbound> findAll ();

    Inbound findById (Long id);

    void save (Inbound inbound);

    void deleteById (Long id);
}
