package com.project.CheapCheese.controllers;

import com.project.CheapCheese.models.ExcelGenerator;
import com.project.CheapCheese.models.classes.Product;
import com.project.CheapCheese.models.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
public class ProductsController {

    private final ProductService service;
    @GetMapping
    public List<Product> takeAllProducts() {
        return service.AllProducts();
    }

    @GetMapping("/export-excel")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Products " + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List <Product> listOfStudents = service.AllProducts();
        ExcelGenerator generator = new ExcelGenerator(listOfStudents);
        generator.generateExcelFile(response);
    }
}
