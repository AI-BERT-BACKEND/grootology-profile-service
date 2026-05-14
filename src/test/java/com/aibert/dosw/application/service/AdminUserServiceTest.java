package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.request.AdminEditUserRequestDTO;
import com.aibert.dosw.application.dto.request.ChangeRoleRequestDTO;
import com.aibert.dosw.application.dto.response.UserSummaryDTO;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.*;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminUserServiceTest {

    @Mock private UserRepositoryPort userRepository;
    @InjectMocks private AdminUserService adminUserService;

    private final UUID adminId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();

    private User buildUser(UUID id) {
        return User.builder()
                .id(id)
                .fullName("Test User")
                .email("test@mail.escuelaing.edu.co")
                .password("hashed")
                .verified(true)
                .role(Role.ESTUDIANTE)
                .status(UserStatus.ACTIVO)
                .profileComplete(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getUserDetail_exitoso_retornaUsuario() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser(userId)));
        UserSummaryDTO result = adminUserService.getUserDetail(userId);
        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    void getUserDetail_noExiste_lanzaException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> adminUserService.getUserDetail(userId));
    }

    @Test
    void updateUserStatus_propiasCuenta_lanzaException() {
        assertThrows(IllegalArgumentException.class,
                () -> adminUserService.updateUserStatus(adminId, adminId, "INACTIVO"));
    }

    @Test
    void deleteUser_propiasCuenta_lanzaException() {
        assertThrows(IllegalArgumentException.class,
                () -> adminUserService.deleteUser(adminId, adminId));
    }

    @Test
    void changeRole_propioRol_lanzaException() {
        ChangeRoleRequestDTO dto = mock(ChangeRoleRequestDTO.class);
        assertThrows(IllegalArgumentException.class,
                () -> adminUserService.changeRole(adminId, adminId, dto));
    }

    @Test
    void listUsers_retornaLista() {
        when(userRepository.findByFilters(any(), any(), any(), any())).thenReturn(List.of(buildUser(userId)));
        List<UserSummaryDTO> result = adminUserService.listUsers(null, null, null, null);
        assertEquals(1, result.size());
    }

    @Test
    void listUsers_conFiltroStatus_retornaLista() {
        when(userRepository.findByFilters(any(), any(), any(), any())).thenReturn(List.of(buildUser(userId)));
        List<UserSummaryDTO> result = adminUserService.listUsers(null, null, "ACTIVO", null);
        assertEquals(1, result.size());
    }

    @Test
    void listUsers_conFiltroRol_retornaLista() {
        when(userRepository.findByFilters(any(), any(), any(), any())).thenReturn(List.of(buildUser(userId)));
        List<UserSummaryDTO> result = adminUserService.listUsers(null, null, null, "ESTUDIANTE");
        assertEquals(1, result.size());
    }

    @Test
    void editUser_exitoso_actualizaDatos() {
        AdminEditUserRequestDTO dto = mock(AdminEditUserRequestDTO.class);
        when(dto.getFullName()).thenReturn("Nuevo Nombre");
        when(dto.getEmail()).thenReturn("nuevo@mail.escuelaing.edu.co");
        when(dto.getRole()).thenReturn(Role.ESTUDIANTE);
        when(dto.getStatus()).thenReturn(UserStatus.ACTIVO);
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser(userId)));
        when(userRepository.existsByEmailAndIdNot(any(), any())).thenReturn(false);
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        UserSummaryDTO result = adminUserService.editUser(adminId, userId, dto);
        assertNotNull(result);
    }

    @Test
    void editUser_correoYaExiste_lanzaException() {
        AdminEditUserRequestDTO dto = mock(AdminEditUserRequestDTO.class);
        when(dto.getEmail()).thenReturn("otro@mail.escuelaing.edu.co");
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser(userId)));
        when(userRepository.existsByEmailAndIdNot(any(), any())).thenReturn(true);
        assertThrows(IllegalArgumentException.class,
                () -> adminUserService.editUser(adminId, userId, dto));
    }

    @Test
    void changeRole_yaEsAdmin_lanzaException() {
        ChangeRoleRequestDTO dto = mock(ChangeRoleRequestDTO.class);
        when(dto.getNewRole()).thenReturn(Role.ADMIN);
        User adminUser = User.builder().id(userId).fullName("Test")
                .email("test@mail.escuelaing.edu.co").password("hashed")
                .verified(true).role(Role.ADMIN).status(UserStatus.ACTIVO)
                .profileComplete(true).createdAt(LocalDateTime.now()).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));
        assertThrows(IllegalArgumentException.class,
                () -> adminUserService.changeRole(adminId, userId, dto));
    }

    @Test
    void changeRole_yaEsEstudiante_lanzaException() {
        ChangeRoleRequestDTO dto = mock(ChangeRoleRequestDTO.class);
        when(dto.getNewRole()).thenReturn(Role.ESTUDIANTE);
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser(userId)));
        assertThrows(IllegalArgumentException.class,
                () -> adminUserService.changeRole(adminId, userId, dto));
    }

    @Test
    void updateUserStatus_exitoso_actualizaEstado() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser(userId)));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        UserSummaryDTO result = adminUserService.updateUserStatus(adminId, userId, "INACTIVO");
        assertNotNull(result);
    }

    @Test
    void updateUserStatus_usuarioNoExiste_lanzaException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> adminUserService.updateUserStatus(adminId, userId, "INACTIVO"));
    }

    @Test
    void deleteUser_exitoso_eliminaUsuario() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser(userId)));
        doNothing().when(userRepository).deleteById(userId);
        assertDoesNotThrow(() -> adminUserService.deleteUser(adminId, userId));
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUser_usuarioNoExiste_lanzaException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> adminUserService.deleteUser(adminId, userId));
    }

    @Test
    void changeRole_exitoso_actualizaRol() {
        ChangeRoleRequestDTO dto = mock(ChangeRoleRequestDTO.class);
        when(dto.getNewRole()).thenReturn(Role.ADMIN);
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser(userId)));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        UserSummaryDTO result = adminUserService.changeRole(adminId, userId, dto);
        assertNotNull(result);
    }

    @Test
    void changeRole_usuarioNoExiste_lanzaException() {
        ChangeRoleRequestDTO dto = mock(ChangeRoleRequestDTO.class);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> adminUserService.changeRole(adminId, userId, dto));
    }
}
