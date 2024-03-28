package com.project.CheapCheese.models.services;

import com.project.CheapCheese.models.classes.Product;
import com.project.CheapCheese.models.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productoRepository;

    public void guardarProducto(Product producto) {
        productoRepository.save(producto);
    }

    public List<Product> AllProducts() {
        return productoRepository.findAll();
    }
}