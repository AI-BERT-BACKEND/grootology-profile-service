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
}
