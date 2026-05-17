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
import org.springframework.mock.web.MockMultipartFile;
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
    void updateProfile_conUserName_actualizaUserName() {
        UpdateProfileDTO dto = mock(UpdateProfileDTO.class);
        when(dto.getUserName()).thenReturn("nuevo_user");
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(userRepository.existsByUserNameAndIdNot("nuevo_user", userId)).thenReturn(false);
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertDoesNotThrow(() -> updateProfileService.updateProfile(userId, dto, null));
    }

    @Test
    void updateProfile_userNameDuplicado_lanzaException() {
        UpdateProfileDTO dto = mock(UpdateProfileDTO.class);
        when(dto.getUserName()).thenReturn("existente");
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(userRepository.existsByUserNameAndIdNot("existente", userId)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> updateProfileService.updateProfile(userId, dto, null));
    }

    @Test
    void updateProfile_sinCambios_mantieneDatosActuales() {
        UpdateProfileDTO dto = mock(UpdateProfileDTO.class);
        when(dto.getUserName()).thenReturn(null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertDoesNotThrow(() -> updateProfileService.updateProfile(userId, dto, null));
    }

    @Test
    void updateProfile_fotoValida_actualizaFoto() {
        UpdateProfileDTO dto = mock(UpdateProfileDTO.class);
        when(dto.getUserName()).thenReturn(null);
        MockMultipartFile photo = new MockMultipartFile("photo", "foto.jpg", "image/jpeg", new byte[1024]);
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(fileUploadService.upload(any())).thenReturn("http://url/foto.jpg");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertDoesNotThrow(() -> updateProfileService.updateProfile(userId, dto, photo));
    }

    @Test
    void updateProfile_fotoFormatoInvalido_lanzaException() {
        UpdateProfileDTO dto = mock(UpdateProfileDTO.class);
        MockMultipartFile photo = new MockMultipartFile("photo", "foto.gif", "image/gif", new byte[1024]);
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        assertThrows(IllegalArgumentException.class, () -> updateProfileService.updateProfile(userId, dto, photo));
    }

    @Test
    void updateProfile_fotoMayorA2MB_lanzaException() {
        UpdateProfileDTO dto = mock(UpdateProfileDTO.class);
        MockMultipartFile photo = new MockMultipartFile("photo", "foto.jpg", "image/jpeg", new byte[3 * 1024 * 1024]);
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        assertThrows(IllegalArgumentException.class, () -> updateProfileService.updateProfile(userId, dto, photo));
    }

    @Test
    void updateProfile_usuarioNoExiste_lanzaException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> updateProfileService.updateProfile(userId, mock(UpdateProfileDTO.class), null));
    }

    @Test
    void changePassword_exitoso_actualizaContrasena() {
        PasswordChangeDTO dto = mock(PasswordChangeDTO.class);
        when(dto.getCurrentPassword()).thenReturn("oldPass");
        when(dto.getNewPassword()).thenReturn("NewPass123");
        when(dto.getConfirmNewPassword()).thenReturn("NewPass123");
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.matches("oldPass", "hashedPassword")).thenReturn(true);
        when(passwordEncoder.matches("NewPass123", "hashedPassword")).thenReturn(false);
        when(passwordEncoder.encode("NewPass123")).thenReturn("newHashed");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertDoesNotThrow(() -> updateProfileService.changePassword(userId, dto));
    }

    @Test
    void changePassword_nuevaIgualActual_lanzaException() {
        PasswordChangeDTO dto = mock(PasswordChangeDTO.class);
        when(dto.getCurrentPassword()).thenReturn("SamePass1");
        when(dto.getNewPassword()).thenReturn("SamePass1");
        when(dto.getConfirmNewPassword()).thenReturn("SamePass1");
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.matches("SamePass1", "hashedPassword")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> updateProfileService.changePassword(userId, dto));
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
}
