package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.AcademicProfileDTO;
import com.aibert.dosw.application.dto.request.PasswordChangeDTO;
import com.aibert.dosw.application.dto.request.UpdateProfileDTO;
import org.springframework.mock.web.MockMultipartFile;
import com.aibert.dosw.application.dto.response.AcademicProfileResponseDTO;
import com.aibert.dosw.domain.ports.in.AcademicProfileUseCase;
import com.aibert.dosw.domain.ports.in.DeleteAccountUseCase;
import com.aibert.dosw.domain.ports.in.UpdateProfileUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    @Mock private AcademicProfileUseCase academicProfileUseCase;
    @Mock private UpdateProfileUseCase updateProfileUseCase;
    @Mock private DeleteAccountUseCase deleteAccountUseCase;
    @InjectMocks private ProfileController profileController;

    private final UUID userId = UUID.randomUUID();

    @Test
    void saveAcademicProfile_exitoso_retorna200() {
        AcademicProfileResponseDTO response = AcademicProfileResponseDTO.builder()
                .profileComplete(true).build();
        when(academicProfileUseCase.saveAcademicProfile(any(), any())).thenReturn(response);

        ResponseEntity<AcademicProfileResponseDTO> result =
                profileController.saveAcademicProfile(userId, mock(AcademicProfileDTO.class));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody().isProfileComplete());
    }

    @Test
    void updateProfile_exitoso_retornaMensaje() {
        doNothing().when(updateProfileUseCase).updateProfile(any(), any(), any());

        ResponseEntity<Map<String, String>> result =
                profileController.updateProfile(userId, mock(UpdateProfileDTO.class));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody().get("message"));
    }

    @Test
    void changePassword_exitoso_retornaMensaje() {
        doNothing().when(updateProfileUseCase).changePassword(any(), any());

        ResponseEntity<Map<String, String>> result =
                profileController.changePassword(userId, mock(PasswordChangeDTO.class));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody().get("message"));
    }

    @Test
    void updateProfilePhoto_exitoso_retornaMensaje() {
        doNothing().when(updateProfileUseCase).updateProfile(any(), any(), any());
        MockMultipartFile photo = new MockMultipartFile("photo", "foto.jpg", "image/jpeg", new byte[1024]);

        ResponseEntity<Map<String, String>> result =
                profileController.updateProfilePhoto(userId, photo);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody().get("message"));
    }

    @Test
    void deleteAccount_exitoso_retornaConfirmacion() {
        doNothing().when(deleteAccountUseCase).deleteAccount(any(), any());

        ResponseEntity<Map<String, Object>> result =
                profileController.deleteAccount(userId, "password123");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue((Boolean) result.getBody().get("accountDeleted"));
        assertTrue((Boolean) result.getBody().get("sessionInvalidated"));
        assertTrue((Boolean) result.getBody().get("redirectLogin"));
    }
}
