package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Supplier;
import org.springframework.data.domain.Page;

public interface SupplierService {
    Page<Supplier> findAll(int page, int size);

    Page<Supplier> findByKeyword(String keyword, int page, int size);

    Supplier findById(Long id);

    void save(Supplier supplier);

    void updatePublishedStatus(Long id, boolean published);

    void deleteById(Long id);


    Page<Supplier> findAllAndSort(int page, int size, String order, String orderBy);

    Page<Supplier> findByKeywordAndSort(String keyword, int page, int size, String order, String orderBy);
}
