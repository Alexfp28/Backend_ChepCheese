package com.project.CheapCheese.repository;

import com.project.CheapCheese.model.Supermarket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupermarketRepository extends MongoRepository<Supermarket, String> {
}
