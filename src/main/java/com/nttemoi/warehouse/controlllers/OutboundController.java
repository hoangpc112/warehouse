package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.dtos.OutboundDTO;
import com.nttemoi.warehouse.dtos.OutboundDetailsDTO;
import com.nttemoi.warehouse.entities.Outbound;
import com.nttemoi.warehouse.entities.OutboundDetails;
import com.nttemoi.warehouse.services.impl.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping ("/outbound")
public class OutboundController {

    private final OutboundServiceImpl outboundService;
    private final OutboundDetailsServiceImpl outboundDetailsService;
    private final UserServiceImpl userService;
    private final WarehouseServiceImpl warehouseService;
    private final ProductServiceImpl productService;
    private final StockBalanceServiceImpl stockBalanceService;

    public OutboundController (OutboundServiceImpl outboundService, UserServiceImpl userService, WarehouseServiceImpl warehouseService, OutboundDetailsServiceImpl outboundDetailsService, ProductServiceImpl productService, StockBalanceServiceImpl stockBalanceService) {
        this.outboundService = outboundService;
        this.userService = userService;
        this.warehouseService = warehouseService;
        this.outboundDetailsService = outboundDetailsService;
        this.productService = productService;
        this.stockBalanceService = stockBalanceService;
    }

    @GetMapping
    public String showOutbound (Model model,
                                @RequestParam (required = false) Optional <String> keyword,
                                @RequestParam (defaultValue = "1") int page,
                                @RequestParam (defaultValue = "10") int size,
                                @RequestParam (defaultValue = "asc") String order,
                                @RequestParam (defaultValue = "id") String orderBy) {

        Page <Outbound> outboundPage;

        if (keyword.isPresent()) {
            outboundPage = outboundService.findAllByUser(userService.findByUsername(keyword.get()), page - 1, size, order, orderBy);
            model.addAttribute("keyword", keyword.get());
        }
        else {
            outboundPage = outboundService.findAll(page - 1, size, order, orderBy);
        }

        if (!orderBy.equals("id")) {
            model.addAttribute("order", order);
            model.addAttribute("orderBy", orderBy);
        }

        model.addAttribute("outbounds", outboundPage.getContent());
        model.addAttribute("currentPage", outboundPage.getNumber() + 1);
        model.addAttribute("totalItems", outboundPage.getTotalElements());
        model.addAttribute("totalPages", outboundPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("filter", "username");

        return "show-outbound";
    }

    @GetMapping ("/info/{id}")
    public String info (@PathVariable ("id") Long id,
                        Model model,
                        @RequestParam (required = false) Optional <String> keyword,
                        @RequestParam (defaultValue = "1") int page,
                        @RequestParam (defaultValue = "10") int size,
                        @RequestParam (defaultValue = "asc") String order,
                        @RequestParam (defaultValue = "id") String orderBy) {
        Outbound outbound = outboundService.findById(id);

        Page <OutboundDetails> outboundDetailsPage;

        if (keyword.isPresent()) {
            outboundDetailsPage = outboundDetailsService.findAllByKeyword(id, "%" + keyword.get() + "%", page - 1, size, order, orderBy);
            model.addAttribute("keyword", keyword.get());
        }
        else {
            outboundDetailsPage = outboundDetailsService.findAll(id, page - 1, size, order, orderBy);
        }

        if (!orderBy.equals("id")) {
            model.addAttribute("order", order);
            model.addAttribute("orderBy", orderBy);
        }

        model.addAttribute("outboundDetails", outboundDetailsPage.getContent());
        model.addAttribute("currentPage", outboundDetailsPage.getNumber() + 1);
        model.addAttribute("totalItems", outboundDetailsPage.getTotalElements());
        model.addAttribute("totalPages", outboundDetailsPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("outbound", outbound);

        return "info-outbound";
    }

    @GetMapping ("/new")
    public String add (Model model) {
        OutboundDTO outboundDTO = new OutboundDTO();
        model.addAttribute("outboundDTO", outboundDTO);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("title", "Add new");
        return "add-outbound";
    }

    @PostMapping ("/save")
    public String save (@Valid @ModelAttribute OutboundDTO outboundDTO,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            return "add-outbound";
        }

        try {
            Outbound outbound = new Outbound();

            if (Optional.ofNullable(outboundDTO.getId()).isPresent()) {
                outbound.setId(outboundDTO.getId());
                outbound.setOutboundDetails(outboundService.findById(outboundDTO.getId()).getOutboundDetails());
            }
            outbound.setUser(userService.findById(outboundDTO.getUserId()));
            outbound.setStatus(outboundDTO.getStatus());
            outbound.setDescription(outboundDTO.getDescription());

            outboundService.save(outbound);
            redirectAttributes.addFlashAttribute("message", "Outbound has been saved successfully!");

            return "redirect:/outbound";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/outbound/new";
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
            Outbound outbound = outboundService.findById(id);

            Page <OutboundDetails> outboundDetailsPage;

            if (keyword.isPresent()) {
                outboundDetailsPage = outboundDetailsService.findAllByKeyword(id, "%" + keyword.get() + "%", page - 1, size, order, orderBy);
                model.addAttribute("keyword", keyword.get());
            }
            else {
                outboundDetailsPage = outboundDetailsService.findAll(id, page - 1, size, order, orderBy);
            }

            OutboundDTO outboundDTO = new OutboundDTO();
            outboundDTO.setId(outbound.getId());
            outboundDTO.setUserId(outbound.getUser().getId());
            outboundDTO.setStatus(outbound.getStatus());
            outboundDTO.setDescription(outbound.getDescription());

            if (!orderBy.equals("id")) {
                model.addAttribute("order", order);
                model.addAttribute("orderBy", orderBy);
            }

            model.addAttribute("outboundDetails", outboundDetailsPage.getContent());
            model.addAttribute("currentPage", outboundDetailsPage.getNumber() + 1);
            model.addAttribute("totalItems", outboundDetailsPage.getTotalElements());
            model.addAttribute("totalPages", outboundDetailsPage.getTotalPages());
            model.addAttribute("pageSize", size);

            model.addAttribute("outboundDTO", outboundDTO);
            model.addAttribute("users", userService.findAll());
            model.addAttribute("title", "Edit");

            return "add-outbound";

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
            return "redirect:/outbound";
        }
    }

    @GetMapping ("/delete/{id}")
    public String delete (@PathVariable ("id") Long id,
                          RedirectAttributes redirectAttributes) {
        try {
            Outbound outbound = outboundService.findById(id);
            outbound.getOutboundDetails().forEach(outboundDetails -> outboundDetailsService.deleteById(outboundDetails.getId()));
            outboundService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Outbound has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/outbound";
    }

    @GetMapping ("/{id}/details/{detailsId}")
    public String showDetails (@PathVariable ("id") Long id,
                               @PathVariable ("detailsId") Long detailsId,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            OutboundDetails outboundDetails = outboundDetailsService.findById(detailsId);

            OutboundDetailsDTO outboundDetailsDTO = new OutboundDetailsDTO();

            outboundDetailsDTO.setId(outboundDetails.getId());
            outboundDetailsDTO.setOutboundId(outboundDetails.getOutbound().getId());
            outboundDetailsDTO.setProductId(outboundDetails.getProduct().getId());
            outboundDetailsDTO.setWarehouseId(outboundDetails.getWarehouse().getId());
            outboundDetailsDTO.setQuantity(outboundDetails.getQuantity());
            outboundDetailsDTO.setStatus(outboundDetails.getStatus());

            model.addAttribute("outboundDetailsDTO", outboundDetailsDTO);
            model.addAttribute("warehouses", warehouseService.findAll());
            model.addAttribute("products", productService.findAll());
            model.addAttribute("title", "Edit");

            return "add-outbound-details";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/outbound" + id;
        }
    }

    @GetMapping ("/{id}/details/new")
    public String addDetails (@PathVariable ("id") Long id,
                              Model model,
                              @RequestParam (required = false) Optional <Long> warehouseId) {
        OutboundDetailsDTO outboundDetailsDTO = new OutboundDetailsDTO();
        outboundDetailsDTO.setOutboundId(id);

        model.addAttribute("outboundDetailsDTO", outboundDetailsDTO);
        model.addAttribute("warehouses", warehouseService.findAll());
        if (warehouseId.isPresent()) {
            model.addAttribute("stockBalance", stockBalanceService.findAllByWarehouseId(warehouseId.get()));
            outboundDetailsDTO.setWarehouseId(warehouseId.get());
        }
        else {
            model.addAttribute("products", productService.findAll());
        }
        model.addAttribute("title", "Add new");

        return "add-outbound-details";
    }

    @PostMapping ("/{id}/details/save")
    public String saveDetails (@PathVariable ("id") Long id,
                               @Valid @ModelAttribute OutboundDetailsDTO outboundDetailsDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        //        if (outboundDetailsDTO.getProductId() != null) {
        //            model.addAttribute("warehouses", warehouseService.findAll());
        //            model.addAttribute("products", stockBalanceService.);
        //        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("warehouses", warehouseService.findAll());
            return "add-outbound-details";
        }

        try {
            OutboundDetails outboundDetails = new OutboundDetails();
            Outbound outbound = outboundService.findById(id);

            if (Optional.ofNullable(outboundDetailsDTO.getId()).isPresent()) {
                outboundDetails.setId(outboundDetailsDTO.getId());
            }
            outboundDetails.setOutbound(outbound);
            outboundDetails.setProduct(productService.findById(outboundDetailsDTO.getProductId()));
            outboundDetails.setWarehouse(warehouseService.findById(outboundDetailsDTO.getWarehouseId()));
            outboundDetails.setQuantity(outboundDetailsDTO.getQuantity());
            outboundDetails.setStatus(outboundDetailsDTO.getStatus());

            outbound.getOutboundDetails().add(outboundDetails);

            outboundDetailsService.save(outboundDetails);
            outboundService.save(outbound);
            redirectAttributes.addFlashAttribute("message", "Outbound details saved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/outbound/" + id + "/details/new";
        }

        return "redirect:/outbound/" + id;
    }

    @GetMapping ("/{id}/details/delete/{detailsId}")
    public String deleteDetails (@PathVariable ("id") Long id,
                                 @PathVariable ("detailsId") Long detailsId,
                                 RedirectAttributes redirectAttributes) {
        try {
            outboundDetailsService.deleteById(detailsId);
            outboundService.save(outboundService.findById(id));
            redirectAttributes.addFlashAttribute("message", "Outbound details deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/outbound/" + id;
    }
}
