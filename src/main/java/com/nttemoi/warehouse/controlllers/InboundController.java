package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.dtos.InboundDTO;
import com.nttemoi.warehouse.dtos.InboundDetailsDTO;
import com.nttemoi.warehouse.entities.Inbound;
import com.nttemoi.warehouse.entities.InboundDetails;
import com.nttemoi.warehouse.services.impl.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping ("/inbound")
public class InboundController {

    private final InboundServiceImpl inboundService;
    private final InboundDetailsServiceImpl inboundDetailsService;
    private final UserServiceImpl userService;
    private final WarehouseServiceImpl warehouseService;
    private final ProductServiceImpl productService;
    private final SupplierServiceImpl supplierService;

    public InboundController (InboundServiceImpl inboundService, UserServiceImpl userService, WarehouseServiceImpl warehouseService, InboundDetailsServiceImpl inboundDetailsService, ProductServiceImpl productService, SupplierServiceImpl supplierService) {
        this.inboundService = inboundService;
        this.userService = userService;
        this.warehouseService = warehouseService;
        this.inboundDetailsService = inboundDetailsService;
        this.productService = productService;
        this.supplierService = supplierService;
    }

    @GetMapping
    public String showInbound (Model model,
                               @RequestParam (required = false) Optional <String> keyword,
                               @RequestParam (defaultValue = "1") int page,
                               @RequestParam (defaultValue = "10") int size,
                               @RequestParam (defaultValue = "asc") String order,
                               @RequestParam (defaultValue = "id") String orderBy) {

        Page <Inbound> inboundPage;

        if (keyword.isPresent()) {
            inboundPage = inboundService.findAllByKeyword("%" + keyword.get() + "%", page - 1, size, order, orderBy);
            model.addAttribute("keyword", keyword.get());
        }
        else {
            inboundPage = inboundService.findAll(page - 1, size, order, orderBy);
        }

        if (!orderBy.equals("id")) {
            model.addAttribute("order", order);
            model.addAttribute("orderBy", orderBy);
        }

        model.addAttribute("inbounds", inboundPage.getContent());
        model.addAttribute("currentPage", inboundPage.getNumber() + 1);
        model.addAttribute("totalItems", inboundPage.getTotalElements());
        model.addAttribute("totalPages", inboundPage.getTotalPages());
        model.addAttribute("pageSize", size);

        return "show-inbound";
    }

    @GetMapping ("/new")
    public String add (Model model) {
        InboundDTO inboundDTO = new InboundDTO();
        model.addAttribute("inboundDTO", inboundDTO);
        model.addAttribute("suppliers", supplierService.findAll());
        model.addAttribute("users", userService.findAll());
        return "add-inbound";
    }

    @PostMapping ("/save")
    public String save (@Valid @ModelAttribute InboundDTO inboundDTO,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("suppliers", supplierService.findAll());
            model.addAttribute("users", userService.findAll());
            return "add-inbound";
        }

        try {
            Inbound inbound = new Inbound();

            if (Optional.ofNullable(inboundDTO.getId()).isPresent()) {
                inbound.setId(inboundDTO.getId());
                inbound.setInboundDetails(inboundService.findById(inboundDTO.getId()).getInboundDetails());
            }
            inbound.setSupplier(supplierService.findById(inboundDTO.getSupplierId()));
            inbound.setUser(userService.findById(inboundDTO.getUserId()));
            inbound.setStatus(inboundDTO.getStatus());
            inbound.setDescription(inboundDTO.getDescription());

            inboundService.save(inbound);
            redirectAttributes.addFlashAttribute("message", "Inbound has been saved successfully!");

            return "redirect:/inbound";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/inbound/new";
        }
    }

    @GetMapping ("/{id}")
    public String edit (@PathVariable ("id") Long id,
                        Model model,
                        RedirectAttributes redirectAttributes,
                        @RequestParam (required = false) Optional <String> keyword,
                        @RequestParam (defaultValue = "1") int page,
                        @RequestParam (defaultValue = "10") int size,
                        @RequestParam (defaultValue = "asc") String order,
                        @RequestParam (defaultValue = "id") String orderBy) {
        try {
            Inbound inbound = inboundService.findById(id);

            Page <InboundDetails> inboundDetailsPage;

            if (keyword.isPresent()) {
                inboundDetailsPage = inboundDetailsService.findAllByKeyword(id, "%" + keyword.get() + "%", page - 1, size, order, orderBy);
                model.addAttribute("keyword", keyword.get());
            }
            else {
                inboundDetailsPage = inboundDetailsService.findAll(id, page - 1, size, order, orderBy);
            }

            InboundDTO inboundDTO = new InboundDTO();
            inboundDTO.setId(inbound.getId());
            inboundDTO.setSupplierId(inbound.getSupplier().getId());
            inboundDTO.setUserId(inbound.getUser().getId());
            inboundDTO.setStatus(inbound.getStatus());
            inboundDTO.setDescription(inbound.getDescription());

            if (!orderBy.equals("id")) {
                model.addAttribute("order", order);
                model.addAttribute("orderBy", orderBy);
            }

            model.addAttribute("inboundDetails", inboundDetailsPage.getContent());
            model.addAttribute("currentPage", inboundDetailsPage.getNumber() + 1);
            model.addAttribute("totalItems", inboundDetailsPage.getTotalElements());
            model.addAttribute("totalPages", inboundDetailsPage.getTotalPages());
            model.addAttribute("pageSize", size);

            model.addAttribute("inboundDTO", inboundDTO);
            model.addAttribute("suppliers", supplierService.findAll());
            model.addAttribute("users", userService.findAll());

            return "add-inbound";

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
            return "redirect:/inbound";
        }
    }

    @GetMapping ("/delete/{id}")
    public String delete (@PathVariable ("id") Long id,
                          RedirectAttributes redirectAttributes) {
        try {
            inboundService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Inbound has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/inbound";
    }

    @GetMapping ("/{id}/details/{detailsId}")
    public String showDetails (@PathVariable ("id") Long id,
                               @PathVariable ("detailsId") Long detailsId,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            InboundDetails inboundDetails = inboundDetailsService.findById(detailsId);

            InboundDetailsDTO inboundDetailsDTO = new InboundDetailsDTO();

            inboundDetailsDTO.setId(inboundDetails.getId());
            inboundDetailsDTO.setInboundId(inboundDetails.getInbound().getId());
            inboundDetailsDTO.setProductId(inboundDetails.getProduct().getId());
            inboundDetailsDTO.setWarehouseId(inboundDetails.getWarehouse().getId());
            inboundDetailsDTO.setQuantity(inboundDetails.getQuantity());
            inboundDetailsDTO.setDamaged(inboundDetails.getDamaged());

            model.addAttribute("inboundDetailsDTO", inboundDetailsDTO);
            model.addAttribute("warehouses", warehouseService.findAll());
            model.addAttribute("products", productService.findAll());

            return "add-inbound-details";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/inbound" + id;
        }
    }

    @GetMapping ("/{id}/details/new")
    public String addDetails (@PathVariable ("id") Long id,
                              Model model) {
        InboundDetailsDTO inboundDetailsDTO = new InboundDetailsDTO();
        inboundDetailsDTO.setInboundId(id);

        model.addAttribute("inboundDetailsDTO", inboundDetailsDTO);
        model.addAttribute("warehouses", warehouseService.findAll());
        model.addAttribute("products", productService.findAll());

        return "add-inbound-details";
    }

    @PostMapping ("/{id}/details/save")
    public String saveDetails (@PathVariable ("id") Long id,
                               @Valid @ModelAttribute InboundDetailsDTO inboundDetailsDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("warehouses", warehouseService.findAll());
            model.addAttribute("products", productService.findAll());
            return "add-inbound-details";
        }

        if (inboundDetailsDTO.getQuantity() < inboundDetailsDTO.getDamaged()) {
            bindingResult.addError(new FieldError("inboundDetailsDTO", "damaged", "Damaged quantity cannot be greater than quantity"));
            model.addAttribute("warehouses", warehouseService.findAll());
            model.addAttribute("products", productService.findAll());
            return "add-inbound-details";
        }

        try {
            InboundDetails inboundDetails = new InboundDetails();
            Inbound inbound = inboundService.findById(id);

            if (Optional.ofNullable(inboundDetailsDTO.getId()).isPresent()) {
                inboundDetails.setId(inboundDetailsDTO.getId());
            }
            inboundDetails.setInbound(inbound);
            inboundDetails.setProduct(productService.findById(inboundDetailsDTO.getProductId()));
            inboundDetails.setWarehouse(warehouseService.findById(inboundDetailsDTO.getWarehouseId()));
            inboundDetails.setQuantity(inboundDetailsDTO.getQuantity());
            inboundDetails.setDamaged(inboundDetailsDTO.getDamaged());

            inbound.getInboundDetails().add(inboundDetails);

            inboundService.save(inbound);
            redirectAttributes.addFlashAttribute("message", "Inbound details saved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/inbound/" + id;
    }

    @GetMapping ("/{id}/details/delete/{detailsId}")
    public String deleteDetails (@PathVariable ("id") Long id,
                                 @PathVariable ("detailsId") Long detailsId,
                                 RedirectAttributes redirectAttributes) {
        try {
            inboundDetailsService.deleteById(detailsId);
            redirectAttributes.addFlashAttribute("message", "Inbound details deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/inbound/" + id;
    }
}
