package com.project.CheapCheese.repositories;

import com.project.CheapCheese.models.classes.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByEmail(String email);

}
