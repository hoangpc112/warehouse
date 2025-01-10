package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List <Product> findAll ();

    Product findById (Long id);

    void save (Product product);

    void deleteById (Long id);

    Page <Product> findAll (int page, int size, String order, String orderBy);

    Page <Product> findAllByKeyword (String keyword, int page, int size, String order, String orderBy);
}