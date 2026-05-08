package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.request.PasswordChangeDTO;
import com.aibert.dosw.application.dto.request.UpdateProfileDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProfileServiceTest {

    @Mock private UserRepositoryPort userRepository;
    @Mock private BCryptPasswordEncoder passwordEncoder;
    @Mock private FileUploadService fileUploadService;
    @InjectMocks private UpdateProfileService updateProfileService;

    private final UUID userId = UUID.randomUUID();

    private User buildUser() {
        return User.builder()
                .id(userId)
                .fullName("Test User")
                .email("test@mail.escuelaing.edu.co")
                .password("hashedPassword")
                .verified(true)
                .role(Role.ESTUDIANTE)
                .status(UserStatus.ACTIVO)
                .profileComplete(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void updateProfile_exitoso_actualizaNombre() {
        UpdateProfileDTO dto = mock(UpdateProfileDTO.class);
        when(dto.getFullName()).thenReturn("Nuevo Nombre");
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        assertDoesNotThrow(() -> updateProfileService.updateProfile(userId, dto, null));
    }

    @Test
    void changePassword_exitoso_actualizaContrasena() {
        PasswordChangeDTO dto = mock(PasswordChangeDTO.class);
        when(dto.getCurrentPassword()).thenReturn("oldPass");
        when(dto.getNewPassword()).thenReturn("NewPass123");
        when(dto.getConfirmNewPassword()).thenReturn("NewPass123");

        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.matches("oldPass", "hashedPassword")).thenReturn(true);
        when(passwordEncoder.encode("NewPass123")).thenReturn("newHashed");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        assertDoesNotThrow(() -> updateProfileService.changePassword(userId, dto));
    }

    @Test
    void changePassword_contrasenaActualIncorrecta_lanzaException() {
        PasswordChangeDTO dto = mock(PasswordChangeDTO.class);
        when(dto.getCurrentPassword()).thenReturn("wrongPass");
        when(dto.getNewPassword()).thenReturn("NewPass123");
        when(dto.getConfirmNewPassword()).thenReturn("NewPass123");

        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.matches("wrongPass", "hashedPassword")).thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> updateProfileService.changePassword(userId, dto));
    }

    @Test
    void changePassword_contrasenasNoCoinciden_lanzaException() {
        PasswordChangeDTO dto = mock(PasswordChangeDTO.class);
        when(dto.getNewPassword()).thenReturn("NewPass123");
        when(dto.getConfirmNewPassword()).thenReturn("Diferente");

        assertThrows(IllegalArgumentException.class, () -> updateProfileService.changePassword(userId, dto));
    }

    @Test
    void updateProfile_usuarioNoExiste_lanzaException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        UpdateProfileDTO dto = mock(UpdateProfileDTO.class);
        assertThrows(UserNotFoundException.class, () -> updateProfileService.updateProfile(userId, dto, null));
    }
}
