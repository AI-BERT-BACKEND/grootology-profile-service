package com.aibert.dosw.domain.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Contraseña actual incorrecta");
    }
}
