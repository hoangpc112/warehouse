package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.Inbound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundRepository extends JpaRepository <Inbound, Long> {
}
