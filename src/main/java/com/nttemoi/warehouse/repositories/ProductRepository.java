package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameLikeOrTypeLike(String name, String type, Pageable pageable);

    @Query("UPDATE Product t SET t.published = :published WHERE t.id = :id")
    @Modifying
    void updatePublishedStatus(Long id, boolean published);
}
