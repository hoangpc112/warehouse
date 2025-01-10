package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Supplier;
import com.nttemoi.warehouse.repositories.SupplierRepository;
import com.nttemoi.warehouse.services.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl (SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List <Supplier> findAll () {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier findById (Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    @Override
    public void save (Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Override
    public void deleteById (Long id) {
        supplierRepository.delete(findById(id));
    }

    @Override
    public Page <Supplier> findAll (int page, int size, String order, String orderBy) {
        return supplierRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), orderBy)));
    }

    @Override
    public Page <Supplier> findAllByKeyword (String keyword, int page, int size, String order, String orderBy) {
        return supplierRepository.findByNameLikeOrPhoneLike(keyword, keyword, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), orderBy)));
    }
}