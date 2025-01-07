package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Product;
import com.nttemoi.warehouse.repositories.ProductRepository;
import com.nttemoi.warehouse.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Page<Product> findAll(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findByKeyword(String keyword, int page, int size) {
        return productRepository.findByNameLikeOrTypeLike("%" + keyword + "%", "%" + keyword + "%", PageRequest.of(page, size));
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updatePublishedStatus(Long id, boolean published) {
        productRepository.updatePublishedStatus(id, published);

    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findAllAndSort(int page, int size, String order, String orderBy) {
        if (order.equals("asc")) {
            return productRepository.findAll(PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        } else {
            return productRepository.findAll(PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }
    }

    @Override
    public Page<Product> findByKeywordAndSort(String keyword, int page, int size, String order, String orderBy) {
        if (order.equals("asc")) {
            return productRepository.findByNameLikeOrTypeLike("%" + keyword + "%", "%" + keyword + "%", PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        } else {
            return productRepository.findByNameLikeOrTypeLike("%" + keyword + "%", "%" + keyword + "%", PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }
    }
}
