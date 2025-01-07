package com.nttemoi.warehouse.controlllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/outbound")
public class OutboundController {

    @GetMapping
    public String outbound () {
        return "show-outbound";
    }

    @GetMapping ("/add")
    public String addOutbound () {
        return "add-outbound";
    }
}
