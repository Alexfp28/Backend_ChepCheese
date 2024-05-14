package com.project.CheapCheese.controller;

import com.project.CheapCheese.model.Supermarket;
import com.project.CheapCheese.service.SupermarketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/supermarkets")
public class SupermarketsController {

    private final SupermarketService service;

    @GetMapping
    public List<Supermarket> takeAllSupermarkets() {
        return service.AllSupermarkets();
    }
}
