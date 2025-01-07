package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Supplier;
import com.nttemoi.warehouse.repositories.SupplierRepository;
import com.nttemoi.warehouse.services.SupplierService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }


    @Override
    public Page<Supplier> findAll(int page, int size) {
        return supplierRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Page<Supplier> findByKeyword(String keyword, int page, int size) {
        return supplierRepository.findByNameLikeOrPhoneLike("%" + keyword + "%", "%" + keyword + "%", PageRequest.of(page, size));
    }

    @Override
    public Supplier findById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Override
    public void updatePublishedStatus(Long id, boolean published) {

    }

    @Override
    public void deleteById(Long id) {
        supplierRepository.deleteById(id);
    }

    @Override
    public Page<Supplier> findAllAndSort(int page, int size, String order, String orderBy) {
        if (order.equals("asc")) {
            return supplierRepository.findAll(PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        } else {
            return supplierRepository.findAll(PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }
    }

    @Override
    public Page<Supplier> findByKeywordAndSort(String keyword, int page, int size, String order, String orderBy) {
        if (order.equals("asc")) {
            return supplierRepository.findByNameLikeOrPhoneLike("%" + keyword + "%", "%" + keyword + "%", PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        } else {
            return supplierRepository.findByNameLikeOrPhoneLike("%" + keyword + "%", "%" + keyword + "%", PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }
    }
}
