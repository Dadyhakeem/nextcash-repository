package com.dev.hakeem.nextcash.exception;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(String massage) {
        super(massage);
    }
}
