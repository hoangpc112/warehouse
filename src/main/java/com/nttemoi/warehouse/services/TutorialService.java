package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.Tutorial;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TutorialService {
    List<Tutorial> findAll();

    Page<Tutorial> findAll(int page, int size);


    Tutorial findById(Long id);

    void save(Tutorial tutorial);

    void updatePublishedStatus(Long id, boolean published);

    void deleteById(Long id);

    Page<Tutorial> findByKeyword(String keyword, int page, int size);

    Page<Tutorial> findAllAndSort(int page, int size, String order, String orderBy);

    Page<Tutorial> findByKeywordAndSort(String keyword, int page, int size, String order, String orderBy);
}
