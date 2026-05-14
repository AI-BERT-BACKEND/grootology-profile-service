package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.request.RegisterRequestDTO;
import com.aibert.dosw.application.dto.response.RegisterResponseDTO;
import com.aibert.dosw.domain.exceptions.EmailAlreadyRegisteredException;
import com.aibert.dosw.domain.exceptions.InvalidTokenException;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.*;
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

import java.time.LocalDateTime;
import java.util.Optional;
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

    private User buildUser(UUID id) {
        return User.builder()
                .id(id)
                .fullName("Test")
                .email("test@mail.escuelaing.edu.co")
                .password("hashed")
                .verified(false)
                .role(Role.ESTUDIANTE)
                .status(UserStatus.ACTIVO)
                .profileComplete(false)
                .build();
    }

    @Test
    void register_exitoso_retornaIdYRol() {
        RegisterRequestDTO request = mock(RegisterRequestDTO.class);
        when(request.getEmail()).thenReturn("nuevo@mail.escuelaing.edu.co");
        when(request.getPassword()).thenReturn("Pass1234");
        when(request.getConfirmPassword()).thenReturn("Pass1234");
        when(request.getFullName()).thenReturn("Nuevo Usuario");
        when(request.getCareer()).thenReturn(Career.INGENIERIA_SISTEMAS);

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("hashed");
        when(userRepository.save(any())).thenReturn(User.builder()
                .id(UUID.randomUUID())
                .email("nuevo@mail.escuelaing.edu.co")
                .role(Role.ESTUDIANTE)
                .status(UserStatus.ACTIVO)
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
        UUID userId = UUID.randomUUID();
        EmailVerificationToken token = EmailVerificationToken.builder()
                .id(1L)
                .token("valid-token")
                .userId(userId)
                .expiresAt(LocalDateTime.now().plusHours(1))
                .used(false)
                .build();

        when(tokenRepository.findByToken("valid-token")).thenReturn(Optional.of(token));
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser(userId)));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(tokenRepository.save(any())).thenReturn(null);

        assertDoesNotThrow(() -> registerService.verifyEmail("valid-token"));
    }

    @Test
    void verifyEmail_tokenNoExiste_lanzaInvalidToken() {
        when(tokenRepository.findByToken("bad-token")).thenReturn(Optional.empty());
        assertThrows(InvalidTokenException.class, () -> registerService.verifyEmail("bad-token"));
    }

    @Test
    void verifyEmail_tokenUsado_lanzaInvalidToken() {
        EmailVerificationToken token = EmailVerificationToken.builder()
                .id(1L)
                .token("used-token")
                .userId(UUID.randomUUID())
                .expiresAt(LocalDateTime.now().plusHours(1))
                .used(true)
                .build();
        when(tokenRepository.findByToken("used-token")).thenReturn(Optional.of(token));
        assertThrows(InvalidTokenException.class, () -> registerService.verifyEmail("used-token"));
    }

    @Test
    void verifyEmail_tokenExpirado_lanzaInvalidToken() {
        EmailVerificationToken token = EmailVerificationToken.builder()
                .id(1L)
                .token("expired-token")
                .userId(UUID.randomUUID())
                .expiresAt(LocalDateTime.now().minusHours(1))
                .used(false)
                .build();
        when(tokenRepository.findByToken("expired-token")).thenReturn(Optional.of(token));
        assertThrows(InvalidTokenException.class, () -> registerService.verifyEmail("expired-token"));
    }

    @Test
    void resendVerificationEmail_usuarioExiste_enviaEmail() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findByEmail("test@mail.escuelaing.edu.co"))
                .thenReturn(Optional.of(buildUser(userId)));
        when(tokenRepository.save(any())).thenReturn(null);
        doNothing().when(emailService).sendVerificationEmail(any(), any());

        assertDoesNotThrow(() -> registerService.resendVerificationEmail("test@mail.escuelaing.edu.co"));
    }

    @Test
    void resendVerificationEmail_usuarioNoExiste_lanzaException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> registerService.resendVerificationEmail("noexiste@mail.escuelaing.edu.co"));
    }

    private EmailVerificationToken buildOtpToken(UUID userId, String otp, boolean used,
            LocalDateTime expiresAt, Integer failedAttempts, LocalDateTime blockedUntil) {
        return EmailVerificationToken.builder()
                .id(1L).token(otp).userId(userId)
                .expiresAt(expiresAt).used(used)
                .failedAttempts(failedAttempts).blockedUntil(blockedUntil)
                .build();
    }

    @Test
    void verifyOtp_codigoCorrecto_verificaCuenta() {
        UUID userId = UUID.randomUUID();
        EmailVerificationToken token = buildOtpToken(userId, "123456", false,
                LocalDateTime.now().plusMinutes(5), 0, null);
        when(tokenRepository.findLatestByUserId(userId)).thenReturn(Optional.of(token));
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser(userId)));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(tokenRepository.save(any())).thenReturn(null);

        var response = registerService.verifyOtp(userId, "123456");

        assertTrue(response.isVerificationStatus());
        assertTrue(response.isAccountStatus());
        assertEquals(0, response.getExpirationTime());
        assertFalse(response.isResendAvailability());
    }

    @Test
    void verifyOtp_codigoIncorrecto_retornaFalso() {
        UUID userId = UUID.randomUUID();
        EmailVerificationToken token = buildOtpToken(userId, "123456", false,
                LocalDateTime.now().plusMinutes(5), 0, null);
        when(tokenRepository.findLatestByUserId(userId)).thenReturn(Optional.of(token));
        when(tokenRepository.save(any())).thenReturn(null);

        var response = registerService.verifyOtp(userId, "999999");

        assertFalse(response.isVerificationStatus());
        assertFalse(response.isAccountStatus());
        assertTrue(response.isResendAvailability());
    }

    @Test
    void verifyOtp_tokenExpirado_retornaResendTrue() {
        UUID userId = UUID.randomUUID();
        EmailVerificationToken token = buildOtpToken(userId, "123456", false,
                LocalDateTime.now().minusMinutes(1), 0, null);
        when(tokenRepository.findLatestByUserId(userId)).thenReturn(Optional.of(token));

        var response = registerService.verifyOtp(userId, "123456");

        assertFalse(response.isVerificationStatus());
        assertTrue(response.isResendAvailability());
    }

    @Test
    void verifyOtp_tokenUsado_retornaResendTrue() {
        UUID userId = UUID.randomUUID();
        EmailVerificationToken token = buildOtpToken(userId, "123456", true,
                LocalDateTime.now().plusMinutes(5), 0, null);
        when(tokenRepository.findLatestByUserId(userId)).thenReturn(Optional.of(token));

        var response = registerService.verifyOtp(userId, "123456");

        assertFalse(response.isVerificationStatus());
        assertTrue(response.isResendAvailability());
    }

    @Test
    void verifyOtp_cuentaBloqueada_retornaBloqueo() {
        UUID userId = UUID.randomUUID();
        EmailVerificationToken token = buildOtpToken(userId, "123456", false,
                LocalDateTime.now().plusMinutes(5), 0, LocalDateTime.now().plusMinutes(10));
        when(tokenRepository.findLatestByUserId(userId)).thenReturn(Optional.of(token));

        var response = registerService.verifyOtp(userId, "123456");

        assertFalse(response.isVerificationStatus());
        assertFalse(response.isResendAvailability());
        assertTrue(response.getExpirationTime() > 0);
    }

    @Test
    void verifyOtp_tercerintentoFallido_bloqueaCuenta() {
        UUID userId = UUID.randomUUID();
        EmailVerificationToken token = buildOtpToken(userId, "123456", false,
                LocalDateTime.now().plusMinutes(5), 2, null);
        when(tokenRepository.findLatestByUserId(userId)).thenReturn(Optional.of(token));
        when(tokenRepository.save(any())).thenReturn(null);

        var response = registerService.verifyOtp(userId, "999999");

        assertFalse(response.isVerificationStatus());
        assertFalse(response.isResendAvailability());
    }

    @Test
    void verifyOtp_tokenNoExiste_lanzaException() {
        UUID userId = UUID.randomUUID();
        when(tokenRepository.findLatestByUserId(userId)).thenReturn(Optional.empty());
        assertThrows(InvalidTokenException.class, () -> registerService.verifyOtp(userId, "123456"));
    }
}
