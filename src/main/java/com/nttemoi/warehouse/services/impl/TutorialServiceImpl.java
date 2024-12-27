package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Tutorial;
import com.nttemoi.warehouse.repositories.TutorialRepository;
import com.nttemoi.warehouse.services.TutorialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;

    public TutorialServiceImpl (TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public Page <Tutorial> findAll (int page, int size) {
        return tutorialRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page <Tutorial> findByKeyword (String keyword, int page, int size) {
        return tutorialRepository.findByTitleLikeOrLevelLike("%" + keyword + "%", "%" + keyword + "%", PageRequest.of(page, size));
    }

    @Override
    public Tutorial findById (Long id) {
        return tutorialRepository.findById(id).orElse(null);
    }

    @Override
    public void save (Tutorial tutorial) {
        tutorialRepository.save(tutorial);
    }

    @Override
    public void updatePublishedStatus (Long id, boolean published) {
        tutorialRepository.updatePublishedStatus(id, published);
    }

    @Override
    public void deleteById (Long id) {
        tutorialRepository.deleteById(id);
    }

    @Override
    public Page <Tutorial> findAllAndSort (int page, int size, String order, String orderBy) {
        if (order.equals("asc")) {
            return tutorialRepository.findAll(PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        }
        else {
            return tutorialRepository.findAll(PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }
    }

    @Override
    public Page <Tutorial> findByKeywordAndSort (String keyword, int page, int size, String order, String orderBy) {
        if (order.equals("asc")) {
            return tutorialRepository.findByTitleLikeOrLevelLike("%" + keyword + "%", "%" + keyword + "%", PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        }
        else {
            return tutorialRepository.findByTitleLikeOrLevelLike("%" + keyword + "%", "%" + keyword + "%", PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }
    }
}
