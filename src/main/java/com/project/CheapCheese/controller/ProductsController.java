package com.project.CheapCheese.controller;

import com.project.CheapCheese.model.generators.EspecificExcelGenerator;
import com.project.CheapCheese.model.generators.GeneralExcelGenerator;
import com.project.CheapCheese.model.Product;
import com.project.CheapCheese.service.ProductService;
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

    // Exporta el .xlsx para que puedas tenerlo en tu equipo y realizar el análisis de datos que tu prefieras.
    @GetMapping("/export-excel")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Products_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Product> listOfProducts = service.AllProducts();
        GeneralExcelGenerator generator = new GeneralExcelGenerator(listOfProducts);
        generator.generateExcelFile(response);
    }

    @GetMapping("/excel")
    public void exportIntoExcelEspecificFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Products_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Product> listOfProducts = service.AllProducts();
        EspecificExcelGenerator generator = new EspecificExcelGenerator(listOfProducts);
        generator.generateExcelFile(response);
    }
}
