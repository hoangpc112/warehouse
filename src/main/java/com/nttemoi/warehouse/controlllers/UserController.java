package com.nttemoi.warehouse.controlllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/user")
public class UserController {

    @GetMapping
    public String showUser () {
        // code here
        return "show-user";
    }

    @GetMapping ("/add")
    public String addUser () {
        return "add-user";
    }
}
