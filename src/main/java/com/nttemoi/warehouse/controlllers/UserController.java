package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.dtos.EditUserDTO;
import com.nttemoi.warehouse.dtos.UserDTO;
import com.nttemoi.warehouse.entities.Role;
import com.nttemoi.warehouse.entities.User;
import com.nttemoi.warehouse.entities.Warehouse;
import com.nttemoi.warehouse.services.impl.UserServiceImpl;
import com.nttemoi.warehouse.services.impl.WarehouseServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private WarehouseServiceImpl warehouseService;

    @GetMapping ("/user")
    public String showUser (Model model,
                            @RequestParam (required = false) String keyword,
                            @RequestParam (required = false) String order,
                            @RequestParam (required = false) String orderBy,
                            @RequestParam (defaultValue = "1") int page,
                            @RequestParam (defaultValue = "10") int size) {
        // code here

        try {
            Page <User> userPage;
            if (keyword == null) {
                if (order != null) {
                    userPage = userService.findAllAndSort(page - 1, size, order, orderBy);
                }
                else {
                    userPage = userService.findAll(page - 1, size);
                }

                // System.out.println(userPage.getSize());
            }
            else {
                if (order != null) {
                    userPage = userService.findByKeywordAndSort("%" + keyword.trim() + "%", page - 1, size, order, orderBy);
                }
                else {
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
            model.addAttribute("totalItems", userPage.getTotalElements());
            model.addAttribute("pageSize", size);
            model.addAttribute("filter", "username");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }


        return "show-user";
    }

    @GetMapping ("/user/{name}")
    public String editUser (@PathVariable String name,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(name);
            EditUserDTO editUserDTO = new EditUserDTO();
            editUserDTO.setId(user.getId());
            editUserDTO.setEmail(user.getEmail());
            editUserDTO.setAddress(user.getAddress());
            editUserDTO.setRoles(user.getRoles());
            editUserDTO.setPhoneNumber(user.getPhoneNumber());
            editUserDTO.setWarehouse(user.getWarehouse());
            editUserDTO.setUsername(user.getUsername());
            List <Warehouse> warehouses = warehouseService.findAll();
            if (user.getWarehouse() != null) {
                warehouses.remove(user.getWarehouse());
            }
            List <Role> userRoleList = userService.findAllRoles();
            model.addAttribute("user", editUserDTO);
            model.addAttribute("userRoleList", userRoleList);
            model.addAttribute("warehouses", warehouses);
            model.addAttribute("pageTitle", "Edit User (Name: " + name + ")");

            return "user_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/user";
    }

    @GetMapping ("/user/new")
    public String newUser (Model model) {
        List <Role> userRoleList = userService.findAllRoles();
        model.addAttribute("userRoleList", userRoleList);
        model.addAttribute("UserDTO", new UserDTO());
        model.addAttribute("success", false);
        return "add-user";
    }

    @PostMapping ("/user/new/save")
    public String saveUser (@Valid @ModelAttribute ("UserDTO") UserDTO UserDTO, BindingResult bindingResult, Model model) {
        if (!UserDTO.validConfirmPassword()) {
            bindingResult.addError(new FieldError("UserDTO", "confirmPassword", "Password and confirm password does not match."));
        }

        User checkUser = userService.findByUsername(UserDTO.getUsername());
        if (checkUser != null) {
            bindingResult.addError(new FieldError("UserDTO", "username", "Username is already in used."));
        }

        if (bindingResult.hasErrors()) {
            List <Role> userRoleList = userService.findAllRoles();
            model.addAttribute("userRoleList", userRoleList);
            return "add-user";
        }

        try {
            var bCryptEncoder = new BCryptPasswordEncoder();

            User user = new User();
            if (!UserDTO.getRoles().isEmpty()) {
                user.setRoles(UserDTO.getRoles());
            }
            user.setUsername(UserDTO.getUsername());
            user.setPassword(bCryptEncoder.encode(UserDTO.getPassword()));

            userService.save(user);
            userService.registerUser(user);
            model.addAttribute("UserDTO", new UserDTO());
            model.addAttribute("success", true);
        } catch (Exception e) {
            bindingResult.addError(new FieldError("UserDTO", "username", e.getMessage()));
            return "add-user";
        }
        return "redirect:/user";
    }


    @GetMapping ("/user/delete/{id}")
    public String deleteUser (@PathVariable long id,
                              RedirectAttributes redirectAttributes) {


        try {
            userService.deleteUser(id);

            redirectAttributes.addFlashAttribute("message", "User has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/user";
    }

    @PostMapping ("/user/save")
    public String saveUser (EditUserDTO user,
                            @RequestParam (required = false) Long warehouseId,
                            RedirectAttributes redirectAttributes) {
        try {
            System.out.println(warehouseId);
            User user1 = userService.findById(user.getId());
            user1.setWarehouse(warehouseService.findById(warehouseId));
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmail());
            user1.setAddress(user.getAddress());
            user1.setRoles(user.getRoles());
            user1.setPhoneNumber(user.getPhoneNumber());

            userService.save(user1);

            redirectAttributes.addFlashAttribute("message", "User has been saved successfully!");
        } catch (Exception e) {
            User user1 = userService.findById(user.getId());
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmail());
            user1.setAddress(user.getAddress());
            user1.setRoles(user.getRoles());
            user1.setPhoneNumber(user.getPhoneNumber());
            userService.save(user1);
            redirectAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/user";
    }

    @GetMapping ("user/{id}/active/{status}")
    public String updateUserActiveStatus (@PathVariable ("id") Long id,
                                          @PathVariable ("status") boolean active,
                                          RedirectAttributes redirectAttributes) {

        try {

            userService.updateUserStatus(id, active);

            String status = active ? "active" : "disabled";
            String message = "User has been " + status;

            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/user";
    }

    @GetMapping ("/user/detail/{name}")
    public String userDetail (@PathVariable String name,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(name);
            List <Role> userRoleList = userService.findAllRoles();
            model.addAttribute("userRoleList", userRoleList);
            model.addAttribute("user", user);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "user_detail";

    }

}
