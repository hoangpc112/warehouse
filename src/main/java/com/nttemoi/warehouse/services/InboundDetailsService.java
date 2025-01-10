package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.InboundDetails;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InboundDetailsService {
    List <InboundDetails> findAll ();

    InboundDetails findById (Long id);

    void save (InboundDetails inboundDetails);

    void deleteById (Long id);

    Page <InboundDetails> findAll (Long inboundId, int page, int size, String order, String orderBy);

    Page <InboundDetails> findAllByKeyword (Long inboundId, String keyword, int page, int size, String order, String orderBy);
}
