package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.RegisterRequestDTO;
import com.aibert.dosw.application.dto.response.OtpVerificationResponseDTO;
import com.aibert.dosw.application.dto.response.RegisterResponseDTO;
import com.aibert.dosw.domain.ports.in.RegisterUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUseCase registerUseCase;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.register(request));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<OtpVerificationResponseDTO> verifyOtp(
            @RequestParam UUID userId,
            @RequestParam String otp) {
        return ResponseEntity.ok(registerUseCase.verifyOtp(userId, otp));
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<String> resend(@RequestParam String email) {
        registerUseCase.resendVerificationEmail(email);
        return ResponseEntity.ok("Código OTP reenviado al correo institucional.");
    }
}
