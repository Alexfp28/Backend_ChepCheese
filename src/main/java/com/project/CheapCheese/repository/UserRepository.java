package com.project.CheapCheese.repository;

import com.project.CheapCheese.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByIdUser(int idUser);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User deleteUserByIdUser(int idUser);

    User deleteUserByEmail(String email);

}
