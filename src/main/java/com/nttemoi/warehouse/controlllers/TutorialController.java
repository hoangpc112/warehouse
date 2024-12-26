package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.entities.Tutorial;
import com.nttemoi.warehouse.services.impl.TutorialServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping ("/tutorials")
public class TutorialController {

    private final TutorialServiceImpl tutorialService;

    public TutorialController (TutorialServiceImpl tutorialService) {
        this.tutorialService = tutorialService;
    }

    @GetMapping
    public String getAll (Model model,
                          @RequestParam (required = false) String keyword,
                          @RequestParam (defaultValue = "1") int page,
                          @RequestParam (defaultValue = "10") int size) {
        Page <Tutorial> pageTuts;
        if (keyword == null) {
            pageTuts = tutorialService.findAll(page - 1, size);
        }
        else {
            pageTuts = tutorialService.findByTitleOrLevel(keyword, page - 1, size);
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("tutorials", pageTuts.getContent());
        model.addAttribute("currentPage", pageTuts.getNumber() + 1);
        model.addAttribute("totalItems", pageTuts.getTotalElements());
        model.addAttribute("totalPages", pageTuts.getTotalPages());
        model.addAttribute("pageSize", size);

        return "tutorials";
    }

    @GetMapping ("/new")
    public String addTutorial (Model model) {
        Tutorial tutorial = new Tutorial();
        tutorial.setPublished(true);

        model.addAttribute("tutorial", tutorial);

        return "tutorial_form";
    }

    @PostMapping ("/save")
    public String saveTutorial (Tutorial tutorial,
                                RedirectAttributes redirectAttributes) {
        tutorialService.save(tutorial);

        redirectAttributes.addFlashAttribute("message", "The Tutorial has been saved successfully!");

        return "redirect:/tutorials";
    }

    @GetMapping ("/{id}")
    public String editTutorial (@PathVariable ("id") Long id,
                                Model model) {
        Tutorial tutorial = tutorialService.findById(id);

        model.addAttribute("tutorial", tutorial);
        model.addAttribute("pageTitle", "Edit Tutorial (ID: " + id + ")");

        return "tutorial_form";
    }

    @GetMapping ("/delete/{id}")
    public String deleteTutorial (@PathVariable ("id") Long id,
                                  RedirectAttributes redirectAttributes) {
        try {
            tutorialService.deleteById(id);

            redirectAttributes.addFlashAttribute("message", "The Tutorial with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/tutorials";
    }

    @GetMapping ("/{id}/published/{status}")
    public String updateTutorialPublishedStatus (@PathVariable ("id") Long id,
                                                 @PathVariable ("status") boolean published,
                                                 RedirectAttributes redirectAttributes) {
        try {
            tutorialService.updatePublishedStatus(id, published);

            String status = published ? "published" : "disabled";
            String message = "The Tutorial id=" + id + " has been " + status;

            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/tutorials";
    }
}