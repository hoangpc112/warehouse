package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Product;
import com.nttemoi.warehouse.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List <Product> findAll () {
        return productRepository.findAll();
    }

    public Product findById (long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void save (Product product) {
        productRepository.save(product);
    }

    public void deleteById (Long id) {
        productRepository.deleteById(id);
    }
}
