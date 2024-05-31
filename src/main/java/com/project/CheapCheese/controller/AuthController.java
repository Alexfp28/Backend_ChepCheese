package com.project.CheapCheese.controller;

import com.project.CheapCheese.payload.request.LoginRequest;
import com.project.CheapCheese.payload.request.RegisterRequest;
import com.project.CheapCheese.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return this.userService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest signUpRequest) {
        return this.userService.register(signUpRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return this.userService.logout();
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getCurrentUser() {
        return this.userService.getCurrentUser();
    }

}
