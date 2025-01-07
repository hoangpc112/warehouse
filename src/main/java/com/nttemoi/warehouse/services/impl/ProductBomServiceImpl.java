package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.ProductBom;
import com.nttemoi.warehouse.repositories.ProductBomRepository;
import com.nttemoi.warehouse.services.ProductBomService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductBomServiceImpl implements ProductBomService {

    @Autowired
    private ProductBomRepository productBomRepository;

    @Override
    public List<ProductBom> findAll() {
        return productBomRepository.findAll();
    }

    @Override
    public ProductBom findById(Long id) {
        return productBomRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ProductBom productbom) {
        productBomRepository.save(productbom);
    }

    @Override
    public void deleteById(Long id) {
        productBomRepository.deleteById(id);
    }

    @Override
    public Page<ProductBom> findAllByProductId(Long productId, int page, int size) {
        return productBomRepository.findByProductId(productId, PageRequest.of(page, size));
    }

    @Override
    public Page<ProductBom> findAllByProductIdAndSort(Long productId, int page, int size, String order, String orderBy) {
        if (order.equals("asc")) {
            return productBomRepository.findByProductId(productId, PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        } else {
            return productBomRepository.findByProductId(productId, PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }
    }

    @Override
    public Page<ProductBom> findByKeywordAndProductId(String keyword, Long productId, int page, int size) {
        return productBomRepository.findByNameLikeAndProductId("%" + keyword + "%", productId, PageRequest.of(page, size));
    }
}
