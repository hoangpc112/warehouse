package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.InboundDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface InboundDetailsRepository extends JpaRepository <InboundDetails, Long> {
    Page <InboundDetails> findAllByInboundId (Long inboundId, Pageable pageable);
}
