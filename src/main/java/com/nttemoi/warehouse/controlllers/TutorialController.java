package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.entities.Tutorial;
import com.nttemoi.warehouse.repositories.TutorialRepository;
import com.nttemoi.warehouse.services.impl.TutorialServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TutorialController {

    private final TutorialRepository tutorialRepository;
    private final TutorialServiceImpl tutorialService;

    public TutorialController (TutorialRepository tutorialRepository, TutorialServiceImpl tutorialService) {
        this.tutorialRepository = tutorialRepository;
        this.tutorialService = tutorialService;
    }

    @GetMapping ("/tutorials")
    public String getAll (Model model,
                          @RequestParam (required = false) String keyword,
                          @RequestParam (defaultValue = "1") int page,
                          @RequestParam (defaultValue = "10") int size) {
        try {
            List <Tutorial> tutorials;
            Pageable paging = PageRequest.of(page - 1, size);

            Page <Tutorial> pageTuts;
            if (keyword == null) {
                pageTuts = tutorialService.findAll(paging);
            }
            else {
                pageTuts = tutorialRepository.findByTitleContainingIgnoreCase(keyword, paging);
                model.addAttribute("keyword", keyword);
            }

            tutorials = pageTuts.getContent();

            model.addAttribute("tutorials", tutorials);
            model.addAttribute("currentPage", pageTuts.getNumber() + 1);
            model.addAttribute("totalItems", pageTuts.getTotalElements());
            model.addAttribute("totalPages", pageTuts.getTotalPages());
            model.addAttribute("pageSize", size);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "tutorials";
    }

    @GetMapping ("/tutorials/new")
    public String addTutorial (Model model) {
        Tutorial tutorial = new Tutorial();
        tutorial.setPublished(true);

        model.addAttribute("tutorial", tutorial);

        return "tutorial_form";
    }

    @PostMapping ("/tutorials/save")
    public String saveTutorial (Tutorial tutorial,
                                RedirectAttributes redirectAttributes) {
        try {
            tutorialRepository.save(tutorial);

            redirectAttributes.addFlashAttribute("message", "The Tutorial has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/tutorials";
    }

    @GetMapping ("/tutorials/{id}")
    public String editTutorial (@PathVariable ("id") Long id,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        try {
            Tutorial tutorial = tutorialService.findById(id);

            model.addAttribute("tutorial", tutorial);
            model.addAttribute("pageTitle", "Edit Tutorial (ID: " + id + ")");

            return "tutorial_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/tutorials";
        }
    }

    @GetMapping ("/tutorials/delete/{id}")
    public String deleteTutorial (@PathVariable ("id") Long id,
                                  RedirectAttributes redirectAttributes) {
        try {
            tutorialRepository.deleteById(id);

            redirectAttributes.addFlashAttribute("message", "The Tutorial with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/tutorials";
    }

    @GetMapping ("/tutorials/{id}/published/{status}")
    public String updateTutorialPublishedStatus (@PathVariable ("id") Long id,
                                                 @PathVariable ("status") boolean published,
                                                 RedirectAttributes redirectAttributes) {
        try {
            tutorialRepository.updatePublishedStatus(id, published);

            String status = published ? "published" : "disabled";
            String message = "The Tutorial id=" + id + " has been " + status;

            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/tutorials";
    }
}