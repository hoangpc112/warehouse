package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Product;
import com.nttemoi.warehouse.repositories.ProductRepository;
import com.nttemoi.warehouse.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List <Product> findAll () {
        return productRepository.findAll();
    }

    @Override
    public Product findById (Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found!"));
    }

    @Override
    public void save (Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById (Long id) {
        productRepository.delete(findById(id));
    }

    @Override
    public Page <Product> findAll (int page, int size, String order, String orderBy) {
        return productRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), orderBy)));
    }

    @Override
    public Page <Product> findAllByKeyword (String keyword, int page, int size, String order, String orderBy) {
        return productRepository.findByNameLikeOrTypeLike(keyword, keyword, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), orderBy)));
    }
}