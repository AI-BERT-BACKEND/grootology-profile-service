package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.RegisterRequestDTO;
import com.aibert.dosw.domain.ports.in.RegisterUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUseCase registerUseCase;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequestDTO request) {
        registerUseCase.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Registro exitoso. Revisa tu correo para verificar tu cuenta."));
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, String>> verify(@RequestParam String token) {
        registerUseCase.verifyEmail(token);
        return ResponseEntity.ok(Map.of("message", "Cuenta verificada exitosamente."));
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<Map<String, String>> resend(@RequestParam String email) {
        registerUseCase.resendVerificationEmail(email);
        return ResponseEntity.ok(Map.of("message", "Correo de verificación reenviado."));
    }
}
