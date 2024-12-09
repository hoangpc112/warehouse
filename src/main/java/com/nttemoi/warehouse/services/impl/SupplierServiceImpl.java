package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Supplier;
import com.nttemoi.warehouse.repositories.SupplierRepository;
import com.nttemoi.warehouse.services.SupplierService;
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
        return supplierRepository.findById(id).orElse(null);
    }

    @Override
    public void save (Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Override
    public void deleteById (Long id) {
        supplierRepository.deleteById(id);
    }
}
