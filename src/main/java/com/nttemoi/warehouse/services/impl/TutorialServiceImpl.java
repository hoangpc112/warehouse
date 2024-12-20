package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Tutorial;
import com.nttemoi.warehouse.repositories.TutorialRepository;
import com.nttemoi.warehouse.services.TutorialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;

    public TutorialServiceImpl (TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public List <Tutorial> findAll () {
        return tutorialRepository.findAll();
    }

    @Override
    public Page <Tutorial> findAll (int page, int size) {
        return tutorialRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page <Tutorial> findByTitleOrLevel (String keyword, int page, int size) {
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
}
