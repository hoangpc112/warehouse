package com.nttemoi.warehouse.controlllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/product")
public class ProductController {

    @GetMapping
    public String product () {
        return "show-product";
    }

    @GetMapping ("/add")
    public String addProduct () {
        return "add-product";
    }
}
