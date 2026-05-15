package com.aibert.dosw.application.service;

import com.aibert.dosw.domain.exceptions.InvalidPasswordException;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.*;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAccountServiceTest {

    @Mock private UserRepositoryPort userRepository;
    @Mock private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks private DeleteAccountService deleteAccountService;

    private final UUID userId = UUID.randomUUID();

    private User buildUser() {
        return User.builder()
                .id(userId).fullName("Test").email("test@mail.escuelaing.edu.co")
                .password("hashedPassword").verified(true).role(Role.ESTUDIANTE)
                .status(UserStatus.ACTIVO).profileComplete(true)
                .createdAt(LocalDateTime.now()).build();
    }

    @Test
    void deleteAccount_contrasenaCorrecta_eliminaCuenta() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.matches("MiPass123", "hashedPassword")).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);

        assertDoesNotThrow(() -> deleteAccountService.deleteAccount(userId, "MiPass123"));
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteAccount_contrasenaIncorrecta_lanzaException() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.matches("wrongPass", "hashedPassword")).thenReturn(false);

        assertThrows(InvalidPasswordException.class,
                () -> deleteAccountService.deleteAccount(userId, "wrongPass"));
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void deleteAccount_usuarioNoExiste_lanzaException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> deleteAccountService.deleteAccount(userId, "MiPass123"));
    }
}
