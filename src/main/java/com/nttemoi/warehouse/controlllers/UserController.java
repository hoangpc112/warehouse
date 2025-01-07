package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.entities.Role;
import com.nttemoi.warehouse.entities.User;
import com.nttemoi.warehouse.entities.Warehouse;
import com.nttemoi.warehouse.services.impl.UserServiceImpl;
import com.nttemoi.warehouse.services.impl.WarehouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private WarehouseServiceImpl warehouseService;

    @GetMapping("/user")
    public String showUser(Model model,
                           @RequestParam(required = false) String keyword,
                           @RequestParam(required = false) String order,
                           @RequestParam(required = false) String orderBy,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int size) {
        // code here

        try {
            Page<User> userPage;
            if (keyword == null) {
                if (order != null) {
                    userPage = userService.findAllAndSort(page - 1, size, order, orderBy);
                } else {
                    userPage = userService.findAll(page - 1, size);
                }

                // System.out.println(userPage.getSize());
            } else {
                if (order != null) {
                    userPage = userService.findByKeywordAndSort("%" + keyword.trim() + "%", page - 1, size, order, orderBy);
                } else {
                    userPage = userService.findByUsername("%" + keyword.trim() + "%", page - 1, size);
                }

                model.addAttribute("keyword", keyword);
            }
            if (order != null) {
                model.addAttribute("order", order);
                model.addAttribute("orderBy", orderBy);
            }

            model.addAttribute("users", userPage.getContent());
            model.addAttribute("currentPage", userPage.getNumber() + 1);
            model.addAttribute("totalPages", userPage.getTotalPages());
            model.addAttribute("totalElements", userPage.getTotalElements());
            model.addAttribute("pageSize", size);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }


        return "show-user";
    }

    @GetMapping("/user/{name}")
    public String editUser(@PathVariable String name,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(name);

            List<Warehouse> warehouses = warehouseService.findAll();
            if (user.getWarehouse() != null) {
                warehouses.remove(user.getWarehouse());
            }
            List<Role> userRoleList = userService.findAllRoles();
            model.addAttribute("user", user);
            model.addAttribute("userRoleList", userRoleList);
            model.addAttribute("warehouses", warehouses);
            model.addAttribute("pageTitle", "Edit User (Name: " + name + ")");

            return "user_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/user";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable long id,
                             RedirectAttributes redirectAttributes) {


        try {
            userService.deleteById(id);

            redirectAttributes.addFlashAttribute("message", "User with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/user";
    }

    @PostMapping("/user/save")
    public String saveUser(User user,
                           @RequestParam(required = false) Long warehouseId,
                           RedirectAttributes redirectAttributes) {
        try {
            System.out.println(warehouseId);
            Warehouse warehouse = warehouseService.findById(warehouseId);
            user.setWarehouse(warehouse);
            userService.save(user);

            redirectAttributes.addFlashAttribute("message", "User has been saved successfully!");
        } catch (Exception e) {
            userService.save(user);
            redirectAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/user";
    }

    @GetMapping("user/{id}/active/{status}")
    public String updateUserActiveStatus(@PathVariable("id") Long id,
                                         @PathVariable("status") boolean active,
                                         RedirectAttributes redirectAttributes) {

        try {

            userService.updateUserStatus(id, active);

            String status = active ? "active" : "disabled";
            String message = "The User id=" + id + " has been " + status;

            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/user";
    }

    @GetMapping("/user/detail/{name}")
    public String userDetail(@PathVariable String name,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(name);
            List<Role> userRoleList = userService.findAllRoles();
            model.addAttribute("userRoleList", userRoleList);
            model.addAttribute("user", user);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "user_detail";

    }

}
