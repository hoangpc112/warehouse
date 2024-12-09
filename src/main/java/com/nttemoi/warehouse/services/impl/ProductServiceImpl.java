package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Product;
import com.nttemoi.warehouse.repositories.ProductRepository;
import com.nttemoi.warehouse.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List <Product> findAll () {
        return productRepository.findAll();
    }

    @Override
    public Product findById (Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void save (Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById (Long id) {
        productRepository.deleteById(id);
    }
}
