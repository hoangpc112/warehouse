package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.Outbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OutboundRepository extends JpaRepository <Outbound, Long> {
}
