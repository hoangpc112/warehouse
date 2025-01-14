package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.Outbound;
import com.nttemoi.warehouse.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OutboundRepository extends JpaRepository <Outbound, Long> {
    Page <Outbound> findAllByUser (User user, Pageable pageable);
}
