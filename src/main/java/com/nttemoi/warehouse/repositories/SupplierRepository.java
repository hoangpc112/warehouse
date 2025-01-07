package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.Supplier;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Page<Supplier> findByNameLikeOrPhoneLike(String name, String phone, Pageable pageable);

    @Query("UPDATE Supplier t SET t.published = :published WHERE t.id = :id")
    @Modifying
    void updatePublishedStatus(Long id, boolean published);

    Supplier findByName(String name);
}