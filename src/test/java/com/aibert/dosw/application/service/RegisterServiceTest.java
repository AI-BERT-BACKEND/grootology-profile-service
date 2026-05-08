package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.request.RegisterRequestDTO;
import com.aibert.dosw.application.dto.response.RegisterResponseDTO;
import com.aibert.dosw.domain.exceptions.EmailAlreadyRegisteredException;
import com.aibert.dosw.domain.model.user.Role;
import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.domain.ports.out.EmailServicePort;
import com.aibert.dosw.domain.ports.out.TokenRepositoryPort;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @Mock private UserRepositoryPort userRepository;
    @Mock private TokenRepositoryPort tokenRepository;
    @Mock private EmailServicePort emailService;
    @Mock private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks private RegisterService registerService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(registerService, "baseUrl", "http://localhost:8081");
    }

    @Test
    void register_exitoso_retornaIdYRol() {
        RegisterRequestDTO request = mock(RegisterRequestDTO.class);
        when(request.getEmail()).thenReturn("nuevo@mail.escuelaing.edu.co");
        when(request.getPassword()).thenReturn("Pass1234");
        when(request.getConfirmPassword()).thenReturn("Pass1234");
        when(request.getFullName()).thenReturn("Nuevo Usuario");
        when(request.getCareer()).thenReturn(com.aibert.dosw.domain.model.user.Career.INGENIERIA_SISTEMAS);
        when(request.getSemester()).thenReturn(3);

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("hashed");
        when(userRepository.save(any())).thenReturn(User.builder()
                .id(UUID.randomUUID())
                .email("nuevo@mail.escuelaing.edu.co")
                .role(Role.ESTUDIANTE)
                .status(com.aibert.dosw.domain.model.user.UserStatus.ACTIVO)
                .build());
        when(tokenRepository.save(any())).thenReturn(null);
        doNothing().when(emailService).sendVerificationEmail(any(), any());

        RegisterResponseDTO response = registerService.register(request);

        assertNotNull(response.getId());
        assertEquals("ESTUDIANTE", response.getRole());
        assertNotNull(response.getMessage());
    }

    @Test
    void register_correoYaExiste_lanzaEmailAlreadyRegistered() {
        RegisterRequestDTO request = mock(RegisterRequestDTO.class);
        when(request.getEmail()).thenReturn("existente@mail.escuelaing.edu.co");
        when(request.getPassword()).thenReturn("Pass1234");
        when(request.getConfirmPassword()).thenReturn("Pass1234");
        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(EmailAlreadyRegisteredException.class, () -> registerService.register(request));
    }

    @Test
    void register_contrasenasNoCoinciden_lanzaIllegalArgument() {
        RegisterRequestDTO request = mock(RegisterRequestDTO.class);
        when(request.getPassword()).thenReturn("Pass1234");
        when(request.getConfirmPassword()).thenReturn("Diferente");

        assertThrows(IllegalArgumentException.class, () -> registerService.register(request));
    }

    @Test
    void verifyEmail_tokenValido_verificaUsuario() {
        com.aibert.dosw.domain.model.user.EmailVerificationToken token =
                com.aibert.dosw.domain.model.user.EmailVerificationToken.builder()
                        .id(UUID.randomUUID())
                        .token("valid-token")
                        .userId(UUID.randomUUID())
                        .expiresAt(java.time.LocalDateTime.now().plusHours(1))
                        .used(false)
                        .build();
        User user = User.builder()
                .id(token.getUserId())
                .fullName("Test")
                .email("test@mail.escuelaing.edu.co")
                .password("hashed")
                .verified(false)
                .role(Role.ESTUDIANTE)
                .status(com.aibert.dosw.domain.model.user.UserStatus.ACTIVO)
                .build();

        when(tokenRepository.findByToken("valid-token")).thenReturn(java.util.Optional.of(token));
        when(userRepository.findById(token.getUserId())).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(tokenRepository.save(any())).thenReturn(null);

        assertDoesNotThrow(() -> registerService.verifyEmail("valid-token"));
    }

    @Test
    void verifyEmail_tokenNoExiste_lanzaInvalidToken() {
        when(tokenRepository.findByToken("bad-token")).thenReturn(java.util.Optional.empty());
        assertThrows(com.aibert.dosw.domain.exceptions.InvalidTokenException.class,
                () -> registerService.verifyEmail("bad-token"));
    }

    @Test
    void verifyEmail_tokenUsado_lanzaInvalidToken() {
        com.aibert.dosw.domain.model.user.EmailVerificationToken token =
                com.aibert.dosw.domain.model.user.EmailVerificationToken.builder()
                        .id(UUID.randomUUID())
                        .token("used-token")
                        .userId(UUID.randomUUID())
                        .expiresAt(java.time.LocalDateTime.now().plusHours(1))
                        .used(true)
                        .build();
        when(tokenRepository.findByToken("used-token")).thenReturn(java.util.Optional.of(token));
        assertThrows(com.aibert.dosw.domain.exceptions.InvalidTokenException.class,
                () -> registerService.verifyEmail("used-token"));
    }

    @Test
    void verifyEmail_tokenExpirado_lanzaInvalidToken() {
        com.aibert.dosw.domain.model.user.EmailVerificationToken token =
                com.aibert.dosw.domain.model.user.EmailVerificationToken.builder()
                        .id(UUID.randomUUID())
                        .token("expired-token")
                        .userId(UUID.randomUUID())
                        .expiresAt(java.time.LocalDateTime.now().minusHours(1))
                        .used(false)
                        .build();
        when(tokenRepository.findByToken("expired-token")).thenReturn(java.util.Optional.of(token));
        assertThrows(com.aibert.dosw.domain.exceptions.InvalidTokenException.class,
                () -> registerService.verifyEmail("expired-token"));
    }

    @Test
    void resendVerificationEmail_usuarioExiste_enviaEmail() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .email("test@mail.escuelaing.edu.co")
                .role(Role.ESTUDIANTE)
                .status(com.aibert.dosw.domain.model.user.UserStatus.ACTIVO)
                .build();
        when(userRepository.findByEmail("test@mail.escuelaing.edu.co")).thenReturn(java.util.Optional.of(user));
        when(tokenRepository.save(any())).thenReturn(null);
        doNothing().when(emailService).sendVerificationEmail(any(), any());

        assertDoesNotThrow(() -> registerService.resendVerificationEmail("test@mail.escuelaing.edu.co"));
    }

    @Test
    void resendVerificationEmail_usuarioNoExiste_lanzaException() {
        when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.empty());
        assertThrows(com.aibert.dosw.domain.exceptions.UserNotFoundException.class,
                () -> registerService.resendVerificationEmail("noexiste@mail.escuelaing.edu.co"));
    }
}
