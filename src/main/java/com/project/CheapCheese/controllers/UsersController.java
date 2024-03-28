package com.project.CheapCheese.controllers;

import com.project.CheapCheese.models.classes.User;
import com.project.CheapCheese.models.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UsersController {

    @Autowired
    private final UsuarioService service;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return service.registrarUsuario(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestParam String email, @RequestParam String password) {
        return service.verificarCredenciales(email, password);
    }

    @GetMapping
    public List<User> takeAllUsers() {
        return service.AllUsers();
    }



}
