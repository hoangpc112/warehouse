package com.nttemoi.warehouse.controlllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/inbound")
public class InboundController {

    @GetMapping
    public String inbound () {
        return "show-inbound";
    }

    @GetMapping ("/add")
    public String addInbound () {
        return "add-inbound";
    }
}
