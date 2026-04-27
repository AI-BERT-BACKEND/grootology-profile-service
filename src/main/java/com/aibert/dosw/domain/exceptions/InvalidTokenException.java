package com.aibert.dosw.domain.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("El enlace de verificación es inválido o ha caducado");
    }
}
