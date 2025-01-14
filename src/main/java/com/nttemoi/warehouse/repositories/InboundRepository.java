package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.Inbound;
import com.nttemoi.warehouse.entities.Supplier;
import com.nttemoi.warehouse.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface InboundRepository extends JpaRepository <Inbound, Long> {
    Page <Inbound> findAllBySupplierOrUser (Supplier supplier, User user, Pageable pageable);
}
