package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.dtos.UserDTO;
import com.nttemoi.warehouse.entities.User;
import com.nttemoi.warehouse.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @GetMapping ("/register")
    public String register (Model model) {
        model.addAttribute("UserDTO", new UserDTO());
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping ("/register")
    public String register (@Valid @ModelAttribute ("UserDTO") UserDTO UserDTO, BindingResult bindingResult, Model model) {
        if (!UserDTO.getPassword().equals(UserDTO.getConfirmPassword())) {
            bindingResult.addError(new FieldError("UserDTO", "confirmPassword", "Mật khẩu và mật khẩu xác nhận lại không giống nhau."));
        }

        User User = userService.findByUsername(UserDTO.getUsername());
        if (User != null) {
            bindingResult.addError(new FieldError("UserDTO", "username", "Username đã có người sử dụng"));
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
        } catch (Exception e) {
            bindingResult.addError(new FieldError("UserDTO", "username", e.getMessage()));
        }

        return "register";
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