package com.project.CheapCheese.models.services;

import com.project.CheapCheese.exceptions.IncorrectCredentialsException;
import com.project.CheapCheese.exceptions.UserNotFoundException;
import com.project.CheapCheese.models.classes.User;
import com.project.CheapCheese.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registrarUsuario(User user) {

        if (user == null) {
            throw new UserNotFoundException("USUARIO SIN INFORMACIÓN!!!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

       return repository.save(user);
    }

    public User verificarCredenciales(String email, String password) {
        User usuario = repository.findUserByEmail(email);

        if (usuario == null) {
            throw new UserNotFoundException("USUARIO NO ENCONTRADO, VUELVE A INTENTARLO!!");
        }

        if (!usuario.getEmail().equals(email)) {
            throw new IncorrectCredentialsException("CORREO INCORRECTO, PRUEBA CON OTRO!!");
        }

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new IncorrectCredentialsException("CONTRASEÑA INCORRECTA, PRUEBA CON OTRA!!");
        }

        return usuario;
    }

    public List<User> AllUsers() {
        return repository.findAll();
    }
}
