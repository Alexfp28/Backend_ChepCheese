package com.project.CheapCheese.controller;

import com.project.CheapCheese.model.generators.SupermarketExcelGenerator;
import com.project.CheapCheese.model.generators.TypeProductExcelGenerator;
import com.project.CheapCheese.model.Product;
import com.project.CheapCheese.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
public class ProductsController {

    private final ProductService service;

    @GetMapping
    public List<Product> takeAllProducts() {
        return service.AllProducts();
    }

    // Exporta el .xlsx para que puedas tenerlo en tu equipo y realizar el análisis de datos que tu prefieras.
    @GetMapping("/products_excel")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Products_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Product> listOfProducts = service.AllProducts();
        TypeProductExcelGenerator generator = new TypeProductExcelGenerator(listOfProducts);
        generator.generateExcelFile(response);
    }

    @GetMapping("/supermarkets_excel")
    public void exportIntoExcelEspecificFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Products_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Product> listOfProducts = service.AllProducts();
        SupermarketExcelGenerator generator = new SupermarketExcelGenerator(listOfProducts);
        generator.generateExcelFile(response);
    }

    @PostMapping("/saveProduct")
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        return this.service.saveProduct(product);
    }

    @PostMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestBody Product product) {
        return this.service.deleteProduct(product);
    }
}
