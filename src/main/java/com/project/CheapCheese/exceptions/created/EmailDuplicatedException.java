package com.project.CheapCheese.exceptions.created;

public class EmailDuplicatedException extends RuntimeException {
    public EmailDuplicatedException(String message) {
        super(message);
    }
}
