package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Inbound;
import org.springframework.data.domain.Page;

public interface InboundService {
    Inbound findById (Long id);

    void save (Inbound inbound);

    void deleteById (Long id);

    void updateStatus (Long id, String status);

    Page <Inbound> findAll (int page, int size, String order, String orderBy);

    Page <Inbound> findAllByKeyword (String keyword, int page, int size, String order, String orderBy);
}
