package com.project.CheapCheese.service;

import com.project.CheapCheese.model.Supermarket;
import com.project.CheapCheese.repository.SupermarketRepository;
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
