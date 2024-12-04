package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.Outbound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundRepository extends JpaRepository <Outbound, Long> {
}
