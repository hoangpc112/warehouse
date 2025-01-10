package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.OutboundDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OutboundDetailsRepository extends JpaRepository <OutboundDetails, Long> {
    Page <OutboundDetails> findByOutboundId (Long outboundId, Pageable pageable);
}
