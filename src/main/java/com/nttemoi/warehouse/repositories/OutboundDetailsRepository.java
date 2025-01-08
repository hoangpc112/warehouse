package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.OutboundDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundDetailsRepository extends JpaRepository <OutboundDetails, Long> {
}
