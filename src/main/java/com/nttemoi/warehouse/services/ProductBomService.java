package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.ProductBom;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductBomService {
    List <ProductBom> findAll ();
    
    ProductBom findById (Long id);

    void save (ProductBom productBom);

    void deleteById (Long id);

    Page <ProductBom> findAll (Long productId, int page, int size, String order, String orderBy);

    Page <ProductBom> findAllByKeyword (Long productId, String keyword, int page, int size, String order, String orderBy);
}