package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.UserAuthUpdateDTO;
import com.aibert.dosw.application.dto.response.UserAuthDTO;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.*;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InternalUserControllerTest {

    @Mock private UserRepositoryPort userRepository;
    @InjectMocks private InternalUserController controller;

    private User buildUser(UUID id) {
        return User.builder()
                .id(id).fullName("Test User").email("test@mail.escuelaing.edu.co")
                .password("hashed").verified(true).role(Role.ESTUDIANTE).status(UserStatus.ACTIVO)
                .career(Career.INGENIERIA_SISTEMAS).profileComplete(true)
                .failedAttempts(0).lockedUntil(null).createdAt(LocalDateTime.now()).build();
    }

    @Test
    void findByEmail_exitoso_retorna200() {
        UUID id = UUID.randomUUID();
        when(userRepository.findByEmail("test@mail.escuelaing.edu.co")).thenReturn(Optional.of(buildUser(id)));

        ResponseEntity<UserAuthDTO> result = controller.findByEmail("test@mail.escuelaing.edu.co");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(id, result.getBody().getId());
        assertEquals("test@mail.escuelaing.edu.co", result.getBody().getEmail());
    }

    @Test
    void findByEmail_noExiste_lanzaExcepcion() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> controller.findByEmail("noexiste@mail.escuelaing.edu.co"));
    }

    @Test
    void findById_exitoso_retorna200() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.of(buildUser(id)));

        ResponseEntity<UserAuthDTO> result = controller.findById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(id, result.getBody().getId());
    }

    @Test
    void findById_noExiste_lanzaExcepcion() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> controller.findById(id));
    }

    @Test
    void updateAuthFields_exitoso_retorna200() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.of(buildUser(id)));
        when(userRepository.save(any())).thenReturn(buildUser(id));

        UserAuthUpdateDTO dto = mock(UserAuthUpdateDTO.class);
        when(dto.getFailedAttempts()).thenReturn(1);
        when(dto.getLockedUntil()).thenReturn(null);

        ResponseEntity<Void> result = controller.updateAuthFields(id, dto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userRepository).save(any());
    }

    @Test
    void updateAuthFields_noExiste_lanzaExcepcion() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> controller.updateAuthFields(id, mock(UserAuthUpdateDTO.class)));
    }
}
