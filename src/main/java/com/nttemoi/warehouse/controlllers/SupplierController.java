package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.dtos.SupplierDTO;
import com.nttemoi.warehouse.entities.Supplier;
import com.nttemoi.warehouse.services.impl.SupplierServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping ("/supplier")
public class SupplierController {

    private final SupplierServiceImpl supplierService;

    public SupplierController (SupplierServiceImpl supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public String getAll (Model model,
                          @RequestParam (required = false) Optional <String> keyword,
                          @RequestParam (defaultValue = "1") int page,
                          @RequestParam (defaultValue = "10") int size,
                          @RequestParam (defaultValue = "asc") String order,
                          @RequestParam (defaultValue = "id") String orderBy) {
        Page <Supplier> supplierPage;

        if (keyword.isPresent()) {
            supplierPage = supplierService.findAllByKeyword("%" + keyword.get() + "%", page - 1, size, order, orderBy);
            model.addAttribute("keyword", keyword.get());
        }
        else {
            supplierPage = supplierService.findAll(page - 1, size, order, orderBy);
        }

        if (!orderBy.equals("id")) {
            model.addAttribute("order", order);
            model.addAttribute("orderBy", orderBy);
        }

        model.addAttribute("suppliers", supplierPage.getContent());
        model.addAttribute("currentPage", supplierPage.getNumber() + 1);
        model.addAttribute("totalItems", supplierPage.getTotalElements());
        model.addAttribute("totalPages", supplierPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("filter", "name, phone number");

        return "show-supplier";
    }

    @GetMapping ("/info/{id}")
    public String info (@PathVariable Long id,
                        Model model) {
        Supplier supplier = supplierService.findById(id);
        model.addAttribute("supplier", supplier);
        return "info-supplier";
    }

    @GetMapping ("/new")
    public String addSupplier (Model model) {
        SupplierDTO supplierDTO = new SupplierDTO();
        model.addAttribute("supplierDTO", supplierDTO);
        model.addAttribute("title", "Add new");
        return "add-supplier";
    }

    @PostMapping ("/save")
    public String saveSupplier (SupplierDTO supplierDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "add-supplier";
        }

        try {
            Supplier supplier = new Supplier();

            if (Optional.ofNullable(supplierDTO.getId()).isPresent()) {
                supplier.setId(supplierDTO.getId());
            }
            supplier.setName(supplierDTO.getName().trim());
            supplier.setAddress(supplierDTO.getAddress().trim());
            supplier.setPhone(supplierDTO.getPhone().trim());
            supplier.setEmail(supplierDTO.getEmail().trim());
            supplier.setStatus(supplierDTO.getStatus());
            supplier.setProducts(supplierDTO.getProducts());

            supplierService.save(supplier);
            redirectAttributes.addFlashAttribute("message", "Supplier has been saved successfully!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/supplier/new";
        }

        return "redirect:/supplier";
    }

    @GetMapping ("/{id}")
    public String editSupplier (@PathVariable Long id,
                                Model model) {
        Supplier supplier = supplierService.findById(id);

        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setId(supplier.getId());
        supplierDTO.setName(supplier.getName());
        supplierDTO.setAddress(supplier.getAddress());
        supplierDTO.setPhone(supplier.getPhone());
        supplierDTO.setEmail(supplier.getEmail());
        supplierDTO.setStatus(supplier.getStatus());
        supplierDTO.setProducts(supplier.getProducts());

        model.addAttribute("supplierDTO", supplierDTO);
        model.addAttribute("title", "Edit");
        return "add-supplier";
    }

    @GetMapping ("/delete/{id}")
    public String deleteSupplier (@PathVariable Long id,
                                  RedirectAttributes redirectAttributes) {
        try {
            supplierService.deleteById(id);

            redirectAttributes.addFlashAttribute("message", "Supplier has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/supplier";
    }
}