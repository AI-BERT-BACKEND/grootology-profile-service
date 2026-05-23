package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.UserAuthUpdateDTO;
import com.aibert.dosw.application.dto.response.UserAuthDTO;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.Role;
import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.domain.model.user.UserStatus;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InternalUserControllerTest {

    @Mock
    private UserRepositoryPort userRepository;

    @InjectMocks
    private InternalUserController controller;

    @Test
    void findByEmail_retornaUserAuthDTO() {
        User user = baseUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        ResponseEntity<UserAuthDTO> response = controller.findByEmail(user.getEmail());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getId(), response.getBody().getId());
        assertEquals(user.getEmail(), response.getBody().getEmail());
        assertEquals(user.getPassword(), response.getBody().getPassword());
        assertEquals(user.getStatus(), response.getBody().getStatus());
    }

    @Test
    void findByEmail_cuandoNoExiste_lanzaUserNotFound() {
        when(userRepository.findByEmail("missing@mail.escuelaing.edu.co")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> controller.findByEmail("missing@mail.escuelaing.edu.co"));
    }

    @Test
    void findById_retornaUserAuthDTO() {
        User user = baseUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<UserAuthDTO> response = controller.findById(user.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getFullName(), response.getBody().getFullName());
        assertEquals(user.getRole(), response.getBody().getRole());
    }

    @Test
    void updateAuthFields_actualizaCamposYRetorna200() {
        User user = baseUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UserAuthUpdateDTO dto = new UserAuthUpdateDTO();
        LocalDateTime lockedUntil = LocalDateTime.now().plusMinutes(10);
        ReflectionTestUtils.setField(dto, "failedAttempts", 3);
        ReflectionTestUtils.setField(dto, "lockedUntil", lockedUntil);

        ResponseEntity<Void> response = controller.updateAuthFields(user.getId(), dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();
        assertEquals(3, saved.getFailedAttempts());
        assertEquals(lockedUntil, saved.getLockedUntil());
        assertEquals(user.getEmail(), saved.getEmail());
        assertEquals(user.getPasswordVersion(), saved.getPasswordVersion());
    }

    @Test
    void updateAuthFields_cuandoNoExiste_lanzaUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> controller.updateAuthFields(id, new UserAuthUpdateDTO()));
    }

    private User baseUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .fullName("Test User")
                .email("test@mail.escuelaing.edu.co")
                .password("hashed-password")
                .verified(true)
                .role(Role.ESTUDIANTE)
                .status(UserStatus.ACTIVO)
                .profileComplete(true)
                .passwordVersion(2)
                .failedAttempts(0)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
