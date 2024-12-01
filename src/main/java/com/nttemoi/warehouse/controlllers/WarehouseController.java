package com.nttemoi.warehouse.controlllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/warehouse")
public class WarehouseController {

    @GetMapping
    public String warehouse () {
        return "show-warehouse";
    }

    @GetMapping ("/add")
    public String addWarehouse () {
        return "add-warehouse";
    }
}
