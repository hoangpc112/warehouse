package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Inbound;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InboundService {
    List <Inbound> findAll ();

    Inbound findById (Long id);

    void save (Inbound inbound);

    void deleteById (Long id);

    Page <Inbound> findAll (int page, int size, String order, String orderBy);

    Page <Inbound> findAllByKeyword (String keyword, int page, int size, String order, String orderBy);
}
