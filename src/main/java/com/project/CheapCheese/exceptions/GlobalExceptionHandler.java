package com.project.CheapCheese.exceptions;

import com.project.CheapCheese.exceptions.created.EmailDuplicatedException;
import com.project.CheapCheese.exceptions.created.IncorrectCredentialsException;
import com.project.CheapCheese.exceptions.created.InvalidCredentialsException;
import com.project.CheapCheese.exceptions.created.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException user) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(user.getMessage());
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<String> handleIncorrectCredentialsException(IncorrectCredentialsException user) {
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException user) {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user.getMessage());
    }

    @ExceptionHandler(EmailDuplicatedException.class)
    public ResponseEntity<String> handleEmailDuplicatedException(EmailDuplicatedException user) {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user.getMessage());
    }
}
