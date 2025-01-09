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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseServiceImpl warehouseService;
    @Autowired
    private StockBalanceServiceImpl stockBalanceService;


    @GetMapping
    public String showWarehouse(Model model,
                                @RequestParam(required = false) String keyword,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(required = false) String order,
                                @RequestParam(required = false) String orderBy
    ) {

        try {
            Page<Warehouse> warehousePage;
            if (keyword == null) {
                if (order != null) {
                    warehousePage = warehouseService.findAllAndSort(page - 1, size, order, orderBy);
                } else {
                    warehousePage = warehouseService.findAll(page - 1, size);
                }

                // System.out.println(userPage.getSize());
            } else {
                if (order != null) {
                    warehousePage = warehouseService.findByKeywordAndSort(keyword, page - 1, size, order, orderBy);
                } else {
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
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }


        return "show-warehouse";
    }

    @PostMapping("/save")
    public String save(EditWarehouseDTO warehouse,
                       RedirectAttributes redirectAttributes) {
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


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        System.out.println(id);
        System.out.println(warehouseService.findById(id).getAddress());
        try {

            warehouseService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Warehouse with id= " + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/warehouse";
    }

    @GetMapping("/new")
    public String newWarehouse(Model model) {
        //  Warehouse warehouse = new Warehouse();
        AddWarehouseDTO addWarehouseDTO = new AddWarehouseDTO();
        model.addAttribute("warehouse", addWarehouseDTO);
        return "add-warehouse";
    }

    @PostMapping("/new/save")
    public String saveNew(AddWarehouseDTO warehouse,
                          RedirectAttributes redirectAttributes) {
        try {
            Warehouse newWarehouse = new Warehouse();
            newWarehouse.setCapacity(warehouse.getWarehouseCapacity());
            newWarehouse.setAddress(warehouse.getWarehouseAddress());
            newWarehouse.setPhoneNumber(warehouse.getWarehousePhoneNumber());
            newWarehouse.setActive(warehouse.isWarehouseActive());
            warehouseService.save(newWarehouse);
            redirectAttributes.addFlashAttribute("message", "Warehouse has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/warehouse";
    }

    @GetMapping("/{id}/active/{status}")
    public String active(@PathVariable("id") Long id,
                         @PathVariable("status") boolean active,
                         RedirectAttributes redirectAttributes) {
        try {
            warehouseService.updateWarehouseStatus(id, active);
            String status = (active ? "active" : "disabled");
            String message = "The Warehouse id=" + id + " has been " + status;
            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/warehouse";
    }

    @GetMapping("/detail/{address}")
    public String warehouseDetail(@PathVariable String address,
                                  Model model,

                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(required = false) String order,
                                  @RequestParam(required = false) String orderBy,
                                  RedirectAttributes redirectAttributes) {
        try {
            Warehouse warehouse = warehouseService.findByAddress(address);
            Page<StockBalance> stockBalancePage;
            if (order != null) {

                stockBalancePage = stockBalanceService.findByWarehouseIdAndSort(warehouse.getId(), page - 1, size, order, orderBy);
            } else {
                stockBalancePage = stockBalanceService.findByWarehouseId(warehouse.getId(), page - 1, size);
            }
            if (order != null) {
                model.addAttribute("order", order);
                model.addAttribute("orderBy", orderBy);
            }
//
//            Page<StockBalance> stockBalancePage = new PageImpl<>(stockBalanceList);
//            for (StockBalance stockBalance : stockBalancePage.getContent()) {
//                System.out.println(stockBalance.getProduct().getName());
//
//                System.out.println(1);
//            }

//            List<StockBalance> stockBalanceList = warehouse.getStockBalances();
//            System.out.println(stockBalanceList.stream().mapToLong(StockBalance::getTotalQuantity).sum());
//            System.out.println(1);
            model.addAttribute("currentPage", stockBalancePage.getNumber() + 1);
            model.addAttribute("totalPages", stockBalancePage.getTotalPages());
            model.addAttribute("totalItems", stockBalancePage.getTotalElements());
            model.addAttribute("pageSize", size);
            model.addAttribute("stockBalancePage", stockBalancePage.getContent());
            model.addAttribute("warehouse", warehouse);
        } catch (Exception e) {
            Warehouse warehouse = warehouseService.findByAddress(address);
            model.addAttribute("warehouse", warehouse);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "warehouse_detail";
    }

    @GetMapping("/{address}")
    public String editWarehouse(@PathVariable String address,
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
