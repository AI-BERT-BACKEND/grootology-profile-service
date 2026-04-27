package com.aibert.dosw.domain.exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException() {
        super("Este correo ya tiene una cuenta asociada");
    }
}
