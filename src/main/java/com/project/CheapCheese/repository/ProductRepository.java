package com.project.CheapCheese.repository;

import com.project.CheapCheese.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
