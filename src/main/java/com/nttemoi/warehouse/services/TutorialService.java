package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TutorialService {
    List <Tutorial> findAll ();

    Page <Tutorial> findAll (Pageable pageable);

    Page <Tutorial> findByTitleContainingIgnoreCase (String keyword, Pageable pageable);

    Tutorial findById (Long id);

    void save (Tutorial tutorial);

    void deleteById (Long id);
}
