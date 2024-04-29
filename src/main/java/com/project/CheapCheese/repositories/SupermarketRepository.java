package com.project.CheapCheese.repositories;

import com.project.CheapCheese.models.classes.Supermarket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupermarketRepository extends MongoRepository<Supermarket, String> {
}
