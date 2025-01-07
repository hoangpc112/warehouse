package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.ProductBom;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductBomService {
    List<ProductBom> findAll();

    ProductBom findById(Long id);

    void save(ProductBom productbom);

    void deleteById(Long id);

    Page<ProductBom> findAllByProductId(Long productId, int page, int size);

    Page<ProductBom> findAllByProductIdAndSort(Long productId, int page, int size, String order, String orderBy);

    Page<ProductBom> findByKeywordAndProductId(String keyword, Long productId, int page, int size);
}

