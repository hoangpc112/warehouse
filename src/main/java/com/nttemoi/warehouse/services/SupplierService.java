package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Supplier;
import com.nttemoi.warehouse.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List <Supplier> findAll () {
        return supplierRepository.findAll();
    }

    public Supplier findById (long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    public void save (Supplier supplier) {
        supplierRepository.save(supplier);
    }

    public void deleteById (Long id) {
        supplierRepository.deleteById(id);
    }
}
