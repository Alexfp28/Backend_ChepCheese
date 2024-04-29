package com.project.CheapCheese.services;

import com.project.CheapCheese.models.classes.Supermarket;
import com.project.CheapCheese.repositories.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupermarketService {

    @Autowired
    public SupermarketRepository repository;

    public void guardarSupermarket(Supermarket supermarket) {
        repository.save(supermarket);
    }

    public List<Supermarket> AllSupermarkets() {
        return repository.findAll();
    }
}
