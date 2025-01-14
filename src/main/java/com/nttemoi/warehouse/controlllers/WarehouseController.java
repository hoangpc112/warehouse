package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.dtos.AddWarehouseDTO;
import com.nttemoi.warehouse.dtos.EditWarehouseDTO;
import com.nttemoi.warehouse.entities.StockBalance;
import com.nttemoi.warehouse.entities.Warehouse;
import com.nttemoi.warehouse.services.impl.StockBalanceServiceImpl;
import com.nttemoi.warehouse.services.impl.WarehouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping ("/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseServiceImpl warehouseService;
    @Autowired
    private StockBalanceServiceImpl stockBalanceService;


    @GetMapping
    public String showWarehouse (Model model,
                                 @RequestParam (required = false) String keyword,
                                 @RequestParam (defaultValue = "1") int page,
                                 @RequestParam (defaultValue = "10") int size,
                                 @RequestParam (required = false) String order,
                                 @RequestParam (required = false) String orderBy
    ) {

        try {
            Page <Warehouse> warehousePage;
            if (keyword == null) {
                if (order != null) {
                    warehousePage = warehouseService.findAllAndSort(page - 1, size, order, orderBy);
                }
                else {
                    warehousePage = warehouseService.findAll(page - 1, size);
                }

                // System.out.println(userPage.getSize());
            }
            else {
                if (order != null) {
                    warehousePage = warehouseService.findByKeywordAndSort(keyword, page - 1, size, order, orderBy);
                }
                else {
                    warehousePage = warehouseService.findByAddress(keyword.trim(), page - 1, size);
                }

                model.addAttribute("keyword", keyword);
            }
            if (order != null) {

                model.addAttribute("order", order);
                model.addAttribute("orderBy", orderBy);
            }
            model.addAttribute("warehouses", warehousePage.getContent());
            model.addAttribute("currentPage", warehousePage.getNumber() + 1);
            model.addAttribute("totalPages", warehousePage.getTotalPages());
            model.addAttribute("totalItems", warehousePage.getTotalElements());
            model.addAttribute("pageSize", size);
            model.addAttribute("filter", "address");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }


        return "show-warehouse";
    }

    @PostMapping ("/save")
    public String save (@ModelAttribute ("warehouse") EditWarehouseDTO warehouse,
                        RedirectAttributes redirectAttributes,
                        BindingResult bindingResult) {
        if (!warehouse.isPhoneNumberValid()) {
            bindingResult.addError(new FieldError("warehouse", "phoneNumber", "Phone number is invalid"));
        }

        if (bindingResult.hasErrors()) {
            return "warehouse-form";
        }
        try {
            Warehouse updatedWarehouse = warehouseService.findById(warehouse.getId());
            updatedWarehouse.setAddress(warehouse.getAddress());
            updatedWarehouse.setPhoneNumber(warehouse.getPhoneNumber());
            updatedWarehouse.setCapacity(warehouse.getCapacity());
            warehouseService.save(updatedWarehouse);
            redirectAttributes.addFlashAttribute("message", "Warehouse has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/warehouse";
    }


    @GetMapping ("/delete/{id}")
    public String delete (@PathVariable Long id,
                          RedirectAttributes redirectAttributes) {
        System.out.println(id);
        System.out.println(warehouseService.findById(id).getAddress());
        try {

            warehouseService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Warehouse has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/warehouse";
    }

    @GetMapping ("/new")
    public String newWarehouse (Model model) {
        //  Warehouse warehouse = new Warehouse();
        AddWarehouseDTO addWarehouseDTO = new AddWarehouseDTO();
        model.addAttribute("warehouse", addWarehouseDTO);
        return "add-warehouse";
    }

    @PostMapping ("/new/save")
    public String saveNew (@ModelAttribute ("warehouse") AddWarehouseDTO warehouse,
                           RedirectAttributes redirectAttributes,
                           BindingResult bindingResult) {
        if (!warehouse.isPhoneNumberValid()) {
            bindingResult.addError(new FieldError("warehouse", "warehousePhoneNumber", "Phone number is invalid"));
        }
        Warehouse checkWarehouse = warehouseService.findByAddress(warehouse.getWarehouseAddress());
        if (checkWarehouse != null) {
            bindingResult.addError(new FieldError("warehouse", "warehouseAddress", "Address is already in use"));
        }

        if (bindingResult.hasErrors()) {
            return "add-warehouse";
        }

        try {
            Warehouse newWarehouse = new Warehouse();
            newWarehouse.setCapacity(warehouse.getWarehouseCapacity());
            newWarehouse.setAddress(warehouse.getWarehouseAddress());
            newWarehouse.setPhoneNumber(warehouse.getWarehousePhoneNumber());
            newWarehouse.setActive(warehouse.isWarehouseActive());
            newWarehouse.setMaxCapacity(warehouse.getWarehouseCapacity());
            warehouseService.save(newWarehouse);
            redirectAttributes.addFlashAttribute("message", "Warehouse has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/warehouse";
    }

    @GetMapping ("/{id}/active/{status}")
    public String active (@PathVariable ("id") Long id,
                          @PathVariable ("status") boolean active,
                          RedirectAttributes redirectAttributes) {
        try {
            warehouseService.updateWarehouseStatus(id, active);
            String status = (active ? "active" : "disabled");
            String message = "Warehouse has been " + status;
            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/warehouse";
    }

    @GetMapping ("/detail/{address}/{id}/active/{status}")
    public String stockBalanceActive (@PathVariable ("address") String address,
                                      @PathVariable ("status") boolean active,
                                      @PathVariable ("id") Long id,
                                      RedirectAttributes redirectAttributes) {
        try {
            String status = (active ? "active" : "disabled");
            stockBalanceService.updateStockBalanceStatus(id, active);
            String message = "The Stock balance has been " + status;
            redirectAttributes.addFlashAttribute("message", message);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/warehouse/detail/" + address;
    }


    @GetMapping ("/detail/{address}")
    public String warehouseDetail (@PathVariable String address,
                                   Model model,

                                   @RequestParam (defaultValue = "1") int page,
                                   @RequestParam (defaultValue = "10") int size,
                                   @RequestParam (required = false) String order,
                                   @RequestParam (required = false) String orderBy,
                                   RedirectAttributes redirectAttributes) {
        try {
            Warehouse warehouse = warehouseService.findByAddress(address);
            Page <StockBalance> stockBalancePage;
            if (order != null) {

                stockBalancePage = stockBalanceService.findByWarehouseIdAndSort(warehouse.getId(), page - 1, size, order, orderBy);
            }
            else {
                stockBalancePage = stockBalanceService.findByWarehouseId(warehouse.getId(), page - 1, size);
            }
            if (order != null) {
                model.addAttribute("order", order);
                model.addAttribute("orderBy", orderBy);
            }

            model.addAttribute("currentPage", stockBalancePage.getNumber() + 1);
            model.addAttribute("totalPages", stockBalancePage.getTotalPages());
            model.addAttribute("totalItems", stockBalancePage.getTotalElements());
            model.addAttribute("pageSize", size);
            model.addAttribute("stockBalancePage", stockBalancePage.getContent());
            model.addAttribute("warehouse", warehouse);
            model.addAttribute("filter", "address");
        } catch (Exception e) {
            Warehouse warehouse = warehouseService.findByAddress(address);
            model.addAttribute("warehouse", warehouse);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "warehouse_detail";
    }

    @GetMapping ("/{address}")
    public String editWarehouse (@PathVariable String address,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        try {
            Warehouse warehouse = warehouseService.findByAddress(address);
            EditWarehouseDTO editWarehouseDTO = new EditWarehouseDTO();

            editWarehouseDTO.setCapacity(warehouse.getCapacity());
            editWarehouseDTO.setAddress(warehouse.getAddress());
            editWarehouseDTO.setPhoneNumber(warehouse.getPhoneNumber());
            editWarehouseDTO.setId(warehouse.getId());
            model.addAttribute("warehouse", editWarehouseDTO);
            model.addAttribute("pageTitle", "Edit Warehouse (address: " + address + ")");
            return "warehouse-form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/warehouse";

    }


}
