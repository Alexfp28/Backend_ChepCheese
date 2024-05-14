package com.project.CheapCheese.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.CheapCheese.jwt.JwtUtils;
import com.project.CheapCheese.model.ERole;
import com.project.CheapCheese.model.Role;
import com.project.CheapCheese.model.User;
import com.project.CheapCheese.payload.request.LoginRequest;
import com.project.CheapCheese.payload.request.RegisterRequest;
import com.project.CheapCheese.payload.response.MessageResponse;
import com.project.CheapCheese.payload.response.UserInfoResponse;
import com.project.CheapCheese.repository.RoleRepository;
import com.project.CheapCheese.repository.UserRepository;
import com.project.CheapCheese.service.UserService;
import com.project.CheapCheese.service.impl.UserDetailsImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class UsersController {

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
}
