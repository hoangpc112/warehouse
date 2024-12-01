package com.nttemoi.warehouse.controlllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/supplier")
public class SupplierController {

    @GetMapping
    public String supplier () {
        return "show-supplier";
    }

    @GetMapping ("/add")
    public String addSupplier () {
        return "add-supplier";
    }
}