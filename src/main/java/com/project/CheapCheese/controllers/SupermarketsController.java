package com.project.CheapCheese.controllers;

import com.project.CheapCheese.models.classes.Supermarket;
import com.project.CheapCheese.models.services.SupermarketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
