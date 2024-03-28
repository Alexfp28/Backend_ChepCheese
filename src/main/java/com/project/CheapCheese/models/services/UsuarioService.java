package com.project.CheapCheese.models.services;

import com.project.CheapCheese.exceptions.IncorrectCredentialsException;
import com.project.CheapCheese.exceptions.InvalidCredentialsException;
import com.project.CheapCheese.exceptions.UserNotFoundException;
import com.project.CheapCheese.models.classes.User;
import com.project.CheapCheese.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsuarioService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registrarUsuario(User user) {

        String regex_password = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$";
        String regex_email = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";


        if (!Pattern.matches(regex_email, user.getEmail())) {
            throw new InvalidCredentialsException("CORREO INCORRECTO, PRUEBA CON OTRO!!!");
        }

        if (!Pattern.matches(regex_password, user.getPassword())) {
            throw new InvalidCredentialsException(
                    "CONTRASEÑA INCORRECTA, PRUEBA CON OTRA!!" +
                            "\n Debe contener al menos:" +
                            "\n - 8 carácteres." +
                            "\n - Mayúscula. (A, B, C, D)" +
                            "\n - Dígito (1, 4, 7, 3, 10)." +
                            "\n - Carácter Especial ( @ , ~ , # )" +
                            "\n !!(NO PUEDE CONTENER ESPACIOS EN BLANCO)!!"
            );

        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

       return repository.save(user);
    }

    public User verificarCredenciales(String email, String password) {
        User usuario = repository.findUserByEmail(email);


        // Si el correo es incorrecto (no concuerda con un usuario) el usuario siempre aparecerá como null
        if (usuario == null) {
            throw new UserNotFoundException("CORREO INCORRECTO, PRUEBA CON OTRO!!");
        }


        // Revisa si la contraseña encriptada (la desencripta primero) coincide con la contraseña introducida
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new IncorrectCredentialsException("CONTRASEÑA INCORRECTA, PRUEBA CON OTRA!!");
        }

        return usuario;
    }

    public List<User> AllUsers() {
        return repository.findAll();
    }
}
