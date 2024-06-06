package com.project.CheapCheese.controller;

import com.project.CheapCheese.model.User;
import com.project.CheapCheese.payload.request.LoginRequest;
import com.project.CheapCheese.payload.request.RegisterRequest;
import com.project.CheapCheese.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        return this.userService.saveUser(user);
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        return this.userService.deleteUser(user);
    }

}
