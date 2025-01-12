package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.ProductBom;
import com.nttemoi.warehouse.repositories.ProductBomRepository;
import com.nttemoi.warehouse.services.ProductBomService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductBomServiceImpl implements ProductBomService {

    private final ProductBomRepository productbomRepository;

    public ProductBomServiceImpl (ProductBomRepository productbomRepository) {
        this.productbomRepository = productbomRepository;
    }

    @Override
    public List <ProductBom> findAll () {
        return productbomRepository.findAll();
    }

    @Override
    public ProductBom findById (Long id) {
        return productbomRepository.findById(id).orElseThrow(() -> new RuntimeException("ProductBom not found"));
    }

    @Override
    public void save (ProductBom productBom) {
        productbomRepository.save(productBom);
    }

    @Override
    public void deleteById (Long id) {
        productbomRepository.delete(findById(id));
    }

    @Override
    public Page <ProductBom> findAll (Long productId, int page, int size, String order, String orderBy) {
        return productbomRepository.findByProductId(productId, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), orderBy)));
    }

    @Override
    public Page <ProductBom> findAllByKeyword (Long productId, String keyword, int page, int size, String order, String orderBy) {
        return productbomRepository.findAllByProductIdAndNameLike(productId, keyword, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), orderBy)));
    }
}