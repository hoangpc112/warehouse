package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.OutboundDetails;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OutboundDetailsService {
    List <OutboundDetails> findAll ();

    OutboundDetails findById (Long id);

    void save (OutboundDetails outboundDetails);

    void deleteById (Long id);

    Page <OutboundDetails> findAll (Long outboundId, int page, int size, String order, String orderBy);

    Page <OutboundDetails> findAllByKeyword (Long outboundId, String keyword, int page, int size, String order, String orderBy);
}
