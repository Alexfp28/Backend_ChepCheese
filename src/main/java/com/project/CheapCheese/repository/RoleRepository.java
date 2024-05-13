package com.project.CheapCheese.repository;

import java.util.Optional;

import com.project.CheapCheese.model.ERole;
import com.project.CheapCheese.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
