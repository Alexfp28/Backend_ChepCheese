package com.project.CheapCheese.controllers;

import com.project.CheapCheese.models.classes.User;
import com.project.CheapCheese.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UsersController {

    private final UsuarioService service;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return service.registrarUsuario(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
       return service.login(user.getEmail(), user.getPassword());
    }

    @GetMapping
    public List<User> takeAllUsers() {
        return service.AllUsers();
    }

}
