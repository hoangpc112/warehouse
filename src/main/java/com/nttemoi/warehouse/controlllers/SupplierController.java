package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.entities.Supplier;
import com.nttemoi.warehouse.services.impl.SupplierServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierServiceImpl supplierService;

    public SupplierController(SupplierServiceImpl supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(required = false) String keyword,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(required = false) String order,
                         @RequestParam(required = false) String orderBy) {
        Page<Supplier> pageTuts;

        if (keyword == null) {
            if (order != null) {
                pageTuts = supplierService.findAllAndSort(page - 1, size, order, orderBy);
            } else {
                pageTuts = supplierService.findAll(page - 1, size);
            }
        } else {
            if (order != null) {
                pageTuts = supplierService.findByKeywordAndSort(keyword, page - 1, size, order, orderBy);
            } else {
                pageTuts = supplierService.findByKeyword(keyword, page - 1, size);
            }
            model.addAttribute("keyword", keyword);
        }
        if (order != null) {
            model.addAttribute("order", order);
            model.addAttribute("orderBy", orderBy);
        }

        model.addAttribute("suppliers", pageTuts.getContent());
        model.addAttribute("currentPage", pageTuts.getNumber() + 1);
        model.addAttribute("totalItems", pageTuts.getTotalElements());
        model.addAttribute("totalPages", pageTuts.getTotalPages());
        model.addAttribute("pageSize", size);

        return "show-supplier";
    }

    @GetMapping("/new")
    public String addSupplier(Model model) {
        Supplier supplier = new Supplier();
        supplier.setPublished(true);

        model.addAttribute("suppliers", supplier);

        return "add-supplier";
    }

    @PostMapping("/save")
    public String saveSupplier(Supplier supplier,
                               RedirectAttributes redirectAttributes) {
        supplierService.save(supplier);

        redirectAttributes.addFlashAttribute("message", "The Supplier has been saved successfully!");
        return "redirect:/supplier";
    }

    @GetMapping("/{id}")
    public String editSupplier(@PathVariable("id") Long id,
                               Model model) {
        Supplier supplier = supplierService.findById(id);

        model.addAttribute("suppliers", supplier);
        model.addAttribute("pageTitle", "Edit Supplier (ID: " + id + ")");

        return "add-supplier";
    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable("id") Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            supplierService.deleteById(id);

            redirectAttributes.addFlashAttribute("message", "The Supplier with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/supplier";
    }

    @GetMapping("/{id}/published/{status}")
    public String updateSupplierPublishedStatus(@PathVariable("id") Long id,
                                                @PathVariable("status") boolean published,
                                                RedirectAttributes redirectAttributes) {
        try {
            supplierService.updatePublishedStatus(id, published);

            String status = published ? "published" : "disabled";
            String message = "The Supplier id=" + id + " has been " + status;

            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/supplier";
    }
}