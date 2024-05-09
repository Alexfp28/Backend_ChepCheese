package com.project.CheapCheese.controllers;

import com.project.CheapCheese.config.JWTUtil;
import com.project.CheapCheese.models.classes.User;
import com.project.CheapCheese.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UsersController {

    private final UsuarioService service;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            String token = JWTUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(service.registrarUsuario(user, token));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

    }

    // TODO: Resetear el token cada vez que quieras logearte.
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        return service.login(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Cerrado sesión.");
    }


    @GetMapping
    public List<User> takeAllUsers() {
        return service.AllUsers();
    }

}
