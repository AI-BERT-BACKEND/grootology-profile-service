package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.RegisterRequestDTO;
import com.aibert.dosw.application.dto.response.*;
import com.aibert.dosw.domain.ports.in.PasswordResetUseCase;
import com.aibert.dosw.domain.ports.in.RegisterUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock private RegisterUseCase registerUseCase;
    @Mock private PasswordResetUseCase passwordResetUseCase;
    @InjectMocks private AuthController authController;

    @Test
    void register_exitoso_retorna201() {
        RegisterResponseDTO response = RegisterResponseDTO.builder()
                .id(UUID.randomUUID()).role("ESTUDIANTE").message("Registro exitoso").build();
        when(registerUseCase.register(any())).thenReturn(response);

        ResponseEntity<RegisterResponseDTO> result = authController.register(mock(RegisterRequestDTO.class));

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("ESTUDIANTE", result.getBody().getRole());
    }

    @Test
    void verifyOtp_exitoso_retorna200() {
        UUID userId = UUID.randomUUID();
        OtpVerificationResponseDTO response = OtpVerificationResponseDTO.builder()
                .verificationStatus(true).accountStatus(true).expirationTime(0).resendAvailability(false).build();
        when(registerUseCase.verifyOtp(userId, "123456")).thenReturn(response);

        ResponseEntity<OtpVerificationResponseDTO> result = authController.verifyOtp(userId, "123456");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody().isVerificationStatus());
    }

    @Test
    void resend_exitoso_retornaMensaje() {
        doNothing().when(registerUseCase).resendVerificationEmail(any());
        ResponseEntity<String> result = authController.resend("test@mail.escuelaing.edu.co");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void forgotPassword_exitoso_retorna200() {
        PasswordResetResponseDTO response = PasswordResetResponseDTO.builder()
                .recoveryStatus(true).expirationTime(5).build();
        when(passwordResetUseCase.requestPasswordReset(any())).thenReturn(response);

        ResponseEntity<PasswordResetResponseDTO> result = authController.forgotPassword("test@mail.escuelaing.edu.co");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody().isRecoveryStatus());
    }

    @Test
    void resetPassword_exitoso_retornaMensaje() {
        doNothing().when(passwordResetUseCase).resetPassword(any(), any(), any());
        ResponseEntity<String> result = authController.resetPassword("token", "NewPass1", "NewPass1");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }
}
