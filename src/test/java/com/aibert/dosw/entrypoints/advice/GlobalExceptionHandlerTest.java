package com.aibert.dosw.entrypoints.advice;

import com.aibert.dosw.domain.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleEmailExists_retorna400() {
        ResponseEntity<Map<String, String>> r = handler.handleEmailExists(new EmailAlreadyRegisteredException());
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
        assertTrue(r.getBody().containsKey("error"));
    }

    @Test
    void handleInvalidToken_retorna400() {
        ResponseEntity<Map<String, String>> r = handler.handleInvalidToken(new InvalidTokenException());
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    }

    @Test
    void handleUserNotFound_retorna404() {
        ResponseEntity<Map<String, String>> r = handler.handleUserNotFound(new UserNotFoundException());
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    }

    @Test
    void handleInvalidPassword_retorna401() {
        ResponseEntity<Map<String, String>> r = handler.handleInvalidPassword(new InvalidPasswordException());
        assertEquals(HttpStatus.UNAUTHORIZED, r.getStatusCode());
    }

    @Test
    void handleIllegalArgument_retorna400() {
        ResponseEntity<Map<String, String>> r = handler.handleIllegalArgument(new IllegalArgumentException("error"));
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    }

    @Test
    void handleValidation_retornaPrimerError() throws Exception {
        Object target = new Object();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, "target");
        bindingResult.addError(new FieldError("target", "email", "Debe ser correo institucional"));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, String>> response = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().get("error").contains("email"));
    }
}
