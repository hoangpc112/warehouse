package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.entities.DateInfo;
import com.nttemoi.warehouse.entities.Prediction;
import com.nttemoi.warehouse.services.impl.ProductServiceImpl;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class HomeController {

    private final ProductServiceImpl productService;

    public HomeController (ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping ("/login")
    public String login () {
        return "login";
    }

    @GetMapping ("/")
    public String home () {
        return "home";
    }

    @GetMapping ("/send")
    public ResponseEntity <List <Prediction>> send (@RequestParam int month, @RequestParam int year) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/predict/top_item";

        DateInfo dateInfo = new DateInfo(month, year);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity <DateInfo> requestEntity = new HttpEntity <>(dateInfo, headers);

        ResponseEntity <List <Prediction>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference <>() {});

        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping ("/receive")
    public ResponseEntity <List <Prediction>> receive (@RequestBody List <Prediction> json) {
        json.forEach(prediction -> {
            prediction.setProductName(productService.findById((long) prediction.getProductId()).getName());
        });
        return ResponseEntity.ok(json);
    }
}