package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Product;

import java.util.List;

public interface ProductService {
    List <Product> findAll ();

    Product findById (Long id);

    void save (Product product);

    void deleteById (Long id);
}
