package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Outbound;
import com.nttemoi.warehouse.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OutboundService {
    List <Outbound> findAll ();

    Outbound findById (Long id);

    void save (Outbound outbound);

    void deleteById (Long id);

    Page <Outbound> findAll (int page, int size, String order, String orderBy);

    Page <Outbound> findAllByUser (User user, int page, int size, String order, String orderBy);
}
