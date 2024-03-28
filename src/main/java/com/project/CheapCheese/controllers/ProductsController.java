package com.project.CheapCheese.controllers;

import com.project.CheapCheese.models.classes.Product;
import com.project.CheapCheese.models.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
public class ProductsController {

    private final ProductService service;

    @GetMapping
    public List<Product> takeAllStudents() {
        return service.AllProducts();
    }
}
