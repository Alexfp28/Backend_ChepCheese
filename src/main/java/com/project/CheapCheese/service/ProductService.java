package com.project.CheapCheese.service;

import com.project.CheapCheese.model.Product;
import com.project.CheapCheese.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productoRepository;

    public ResponseEntity<?> saveProduct(Product productDetails) {

        Product product = productoRepository.findById(productDetails.getId());
        if (product == null)
            return ResponseEntity.ok(productoRepository.save(productDetails));

        product.setTienda(productDetails.getTienda());
        product.setPrecio(productDetails.getPrecio());
        product.setNombre(productDetails.getNombre());
        product.setImagen(productDetails.getImagen());
        return ResponseEntity.ok(productoRepository.save(product));
    }

    public ResponseEntity<?> deleteProduct(Product product) {
        return ResponseEntity.ok(productoRepository.deleteById(product.getId()));
    }

    public List<Product> AllProducts() {
        return productoRepository.findAll();
    }
}