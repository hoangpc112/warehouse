package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.dtos.UserDTO;
import com.nttemoi.warehouse.entities.User;
import com.nttemoi.warehouse.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserServiceImpl userService;

    public HomeController (UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping ("/register")
    public String register (Model model) {
        model.addAttribute("UserDTO", new UserDTO());
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping ("/register")
    public String register (@Valid @ModelAttribute ("UserDTO") UserDTO UserDTO, BindingResult bindingResult, Model model) {
        if (!UserDTO.getPassword().equals(UserDTO.getConfirmPassword())) {
            bindingResult.addError(new FieldError("UserDTO", "confirmPassword", "Password and confirm password does not match."));
        }

        User User = userService.findByUsername(UserDTO.getUsername());
        if (User != null) {
            bindingResult.addError(new FieldError("UserDTO", "username", "Username is already in used."));
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            var bCryptEncoder = new BCryptPasswordEncoder();

            User user = new User();
            user.setUsername(UserDTO.getUsername());
            user.setPassword(bCryptEncoder.encode(UserDTO.getPassword()));

            userService.save(user);

            model.addAttribute("UserDTO", new UserDTO());
            model.addAttribute("success", true);

            return "redirect:/login";
        } catch (Exception e) {
            bindingResult.addError(new FieldError("UserDTO", "username", e.getMessage()));
            return "register";
        }
    }

    @GetMapping ("/login")
    public String login () {
        return "login";
    }

    @GetMapping ("/")
    public String home () {
        return "home";
    }
}