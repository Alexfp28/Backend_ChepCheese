package com.project.CheapCheese.service;

import com.project.CheapCheese.model.Product;
import com.project.CheapCheese.repository.ProductRepository;
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