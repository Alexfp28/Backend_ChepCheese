package com.project.CheapCheese.service;

import com.project.CheapCheese.core.CheapCheese;
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
import com.project.CheapCheese.service.impl.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<?> register(RegisterRequest registerRequest) {

        // Si el username coincide con otro usuario no es válido.
        if (CheapCheese.verifyUserByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: El username ya está en uso!"));
        }
        // Si el email coincide con otro usuario no es válido.
        if (CheapCheese.verifyUserByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: El email ya está en uso!"));
        }

        var regex_password = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$";
        // Si la contraseña no coincide con el regex no es válido
        if (CheapCheese.verifyPassword(regex_password, registerRequest.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Contraseña no válida."));
        }

        Set<String> strRoles = registerRequest.getRoles();
        Set<Role> roles = CheapCheese.asignRoles(strRoles);

        User user = CheapCheese.createUser(registerRequest);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado correctamente!!"));
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    public ResponseEntity<?> logout() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("Has cerrado sesión!"));
    }

    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<?> saveUser(User userDetails) {

        User user = userRepository.findUserByIdUser(userDetails.getIdUser());
        if (user == null)
            return ResponseEntity.ok(userRepository.save(userDetails));

        user.setPassword(CheapCheese.encoder.encode(userDetails.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    public ResponseEntity<?> deleteUser(User user) {
        return ResponseEntity.ok(userRepository.deleteUserByIdUser(user.getIdUser()));
    }

    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
