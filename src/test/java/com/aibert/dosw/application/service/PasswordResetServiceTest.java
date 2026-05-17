package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.response.PasswordResetResponseDTO;
import com.aibert.dosw.domain.exceptions.InvalidTokenException;
import com.aibert.dosw.domain.model.user.*;
import com.aibert.dosw.domain.ports.out.EmailServicePort;
import com.aibert.dosw.domain.ports.out.PasswordResetTokenPort;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetServiceTest {

    @Mock private UserRepositoryPort userRepository;
    @Mock private PasswordResetTokenPort tokenPort;
    @Mock private EmailServicePort emailService;
    @Mock private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks private PasswordResetService passwordResetService;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(passwordResetService, "baseUrl", "http://localhost:8081");
    }

    private User buildUser() {
        return User.builder()
                .id(userId).fullName("Test").email("test@mail.escuelaing.edu.co")
                .password("hashed").verified(true).role(Role.ESTUDIANTE)
                .status(UserStatus.ACTIVO).profileComplete(false)
                .createdAt(LocalDateTime.now()).build();
    }

    private PasswordResetToken buildToken(boolean used, LocalDateTime expiresAt) {
        return PasswordResetToken.builder()
                .id(1L).token("reset-token").userId(userId)
                .expiresAt(expiresAt).used(used).build();
    }

    @Test
    void requestPasswordReset_correoExiste_enviaCorreoYRetornaTrue() {
        when(userRepository.findByEmail("test@mail.escuelaing.edu.co"))
                .thenReturn(Optional.of(buildUser()));
        when(tokenPort.save(any())).thenAnswer(i -> i.getArgument(0));
        doNothing().when(emailService).sendRecoveryEmail(any(), any());

        PasswordResetResponseDTO response = passwordResetService
                .requestPasswordReset("test@mail.escuelaing.edu.co");

        assertTrue(response.isRecoveryStatus());
        assertEquals(5, response.getExpirationTime());
        verify(emailService).sendRecoveryEmail(any(), any());
    }

    @Test
    void requestPasswordReset_correoNoExiste_retornaTrueSinEnviarCorreo() {
        // RN-04: no revelar si el correo existe
        when(userRepository.findByEmail("noexiste@mail.escuelaing.edu.co"))
                .thenReturn(Optional.empty());

        PasswordResetResponseDTO response = passwordResetService
                .requestPasswordReset("noexiste@mail.escuelaing.edu.co");

        assertTrue(response.isRecoveryStatus());
        verify(emailService, never()).sendRecoveryEmail(any(), any());
    }

    @Test
    void resetPassword_tokenValido_actualizaContrasena() {
        when(tokenPort.findByToken("reset-token"))
                .thenReturn(Optional.of(buildToken(false, LocalDateTime.now().plusMinutes(2))));
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.encode("NewPass123")).thenReturn("newHashed");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(tokenPort.save(any())).thenAnswer(i -> i.getArgument(0));

        assertDoesNotThrow(() -> passwordResetService
                .resetPassword("reset-token", "NewPass123", "NewPass123"));
        verify(userRepository).save(any());
    }

    @Test
    void resetPassword_contrasenasNoCoinciden_lanzaException() {
        assertThrows(IllegalArgumentException.class, () ->
                passwordResetService.resetPassword("reset-token", "Pass1234", "Diferente"));
    }

    @Test
    void resetPassword_tokenNoExiste_lanzaInvalidToken() {
        when(tokenPort.findByToken("bad-token")).thenReturn(Optional.empty());
        assertThrows(InvalidTokenException.class, () ->
                passwordResetService.resetPassword("bad-token", "Pass1234", "Pass1234"));
    }

    @Test
    void resetPassword_tokenUsado_lanzaInvalidToken() {
        when(tokenPort.findByToken("reset-token"))
                .thenReturn(Optional.of(buildToken(true, LocalDateTime.now().plusMinutes(2))));
        assertThrows(InvalidTokenException.class, () ->
                passwordResetService.resetPassword("reset-token", "Pass1234", "Pass1234"));
    }

    @Test
    void resetPassword_tokenExpirado_lanzaInvalidToken() {
        when(tokenPort.findByToken("reset-token"))
                .thenReturn(Optional.of(buildToken(false, LocalDateTime.now().minusMinutes(1))));
        assertThrows(InvalidTokenException.class, () ->
                passwordResetService.resetPassword("reset-token", "Pass1234", "Pass1234"));
    }
}
