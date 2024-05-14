package com.project.CheapCheese.core;

import com.project.CheapCheese.model.ERole;
import com.project.CheapCheese.model.Role;
import com.project.CheapCheese.model.User;
import com.project.CheapCheese.payload.request.RegisterRequest;
import com.project.CheapCheese.repository.RoleRepository;
import com.project.CheapCheese.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class CheapCheese {

    static CheapCheese singleton;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected PasswordEncoder encoder;

    public CheapCheese() {
        singleton = this;
    }

    public static boolean verifyUserByUsername(String username) {
        return singleton.userRepository.existsByUsername(username);
    }

    public static boolean verifyUserByEmail(String email) {
        return singleton.userRepository.existsByEmail(email);
    }

    public static boolean verifyPassword(String regex, String password) {
        return !Pattern.matches(regex, password);
    }

    public static User createUser(RegisterRequest registerRequest) {
        // Crea una nueva cuenta de usuario.
        return new User(registerRequest.getUsername(),
                registerRequest.getEmail(),
                singleton.encoder.encode(registerRequest.getPassword()));
    }

    public static Set<Role> asignRoles(Set<String> strRoles) {

        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = singleton.roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role no encontrado."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = singleton.roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role no encontrado."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = singleton.roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role no encontrado."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = singleton.roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role no encontrado."));
                        roles.add(userRole);
                }
            });
        }

        return roles;
    }

}
