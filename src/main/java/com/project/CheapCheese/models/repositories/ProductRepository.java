package com.project.CheapCheese.models.repositories;

import com.project.CheapCheese.models.classes.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
