package com.project.CheapCheese.services;

import com.project.CheapCheese.exceptions.created.EmailDuplicatedException;
import com.project.CheapCheese.exceptions.created.IncorrectCredentialsException;
import com.project.CheapCheese.exceptions.created.InvalidCredentialsException;
import com.project.CheapCheese.exceptions.created.UserNotFoundException;
import com.project.CheapCheese.models.classes.User;
import com.project.CheapCheese.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UsuarioService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registrarUsuario(User user) {

        var existent = repository.findUserByEmail(user.getEmail());
        var regex_password = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$";
        var regex_email = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Si el email introducido es el mismo que tiene algún usuario explota.
        if (existent != null) {
            throw new EmailDuplicatedException("CORREO YA EXISTENTE, PRUEBA CON OTRO!!!");
        }

        // Si no coincide el regex el email no es válido
        if (!Pattern.matches(regex_email, user.getEmail())) {
            throw new InvalidCredentialsException("CORREO INCORRECTO, PRUEBA CON OTRO!!!");
        }

        // Si la contraseña no coincide con el regex no es válido
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

            // Encripta la contraseña
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

       return repository.save(user);
    }

    // Verifica todas las credenciales para saber si el usuario es el correcto
    public User verificarCredenciales(String email, String password) {
        var usuario = repository.findUserByEmail(email);


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


    public ResponseEntity<?> login(String email, String password) {
        User user = verificarCredenciales(email,password);
        if (user != null) {
            return ResponseEntity.ok("Login ha sido exitoso!" );
        }
        return ResponseEntity.badRequest().build();
    }

    // Lista todos los usuarios existentes
    public List<User> AllUsers() {
        return repository.findAll();
    }
}
