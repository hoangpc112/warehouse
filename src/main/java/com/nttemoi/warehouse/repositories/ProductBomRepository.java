package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.ProductBom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBomRepository extends JpaRepository<ProductBom, Long> {
    Page<ProductBom> findByProductId(Long productId, Pageable pageable);

    Page<ProductBom> findByNameLikeAndProductId(String name, Long productId, Pageable pageable);
}
