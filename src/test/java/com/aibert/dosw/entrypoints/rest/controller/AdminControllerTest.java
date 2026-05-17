package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.AdminEditUserRequestDTO;
import com.aibert.dosw.application.dto.request.ChangeRoleRequestDTO;
import com.aibert.dosw.application.dto.response.UserSummaryDTO;
import com.aibert.dosw.domain.model.user.Role;
import com.aibert.dosw.domain.model.user.UserStatus;
import com.aibert.dosw.domain.ports.in.AdminUserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock private AdminUserUseCase adminUserUseCase;
    @InjectMocks private AdminController adminController;

    private final UUID adminId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();

    private Authentication mockAuth() {
        Authentication auth = mock(Authentication.class);
        when(auth.getCredentials()).thenReturn(adminId.toString());
        return auth;
    }

    private UserSummaryDTO buildSummary() {
        return UserSummaryDTO.builder()
                .id(userId).fullName("Test").email("test@mail.escuelaing.edu.co")
                .role(Role.ESTUDIANTE).status(UserStatus.ACTIVO)
                .createdAt(LocalDateTime.now()).build();
    }

    @Test
    void listUsers_retorna200() {
        when(adminUserUseCase.listUsers(any(), any(), any(), any())).thenReturn(List.of(buildSummary()));
        ResponseEntity<List<UserSummaryDTO>> result = adminController.listUsers(null, null, null, null);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void getUserDetail_retorna200() {
        when(adminUserUseCase.getUserDetail(userId)).thenReturn(buildSummary());
        ResponseEntity<UserSummaryDTO> result = adminController.getUserDetail(userId);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userId, result.getBody().getId());
    }

    @Test
    void editUser_retorna200() {
        when(adminUserUseCase.editUser(any(), any(), any())).thenReturn(buildSummary());
        ResponseEntity<UserSummaryDTO> result = adminController.editUser(mockAuth(), userId, mock(AdminEditUserRequestDTO.class));
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void updateStatus_retorna200() {
        when(adminUserUseCase.updateUserStatus(any(), any(), any())).thenReturn(buildSummary());
        ResponseEntity<UserSummaryDTO> result = adminController.updateStatus(mockAuth(), userId, "INACTIVO");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void deleteUser_retorna200() {
        doNothing().when(adminUserUseCase).deleteUser(any(), any());
        ResponseEntity<Map<String, String>> result = adminController.deleteUser(mockAuth(), userId);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody().get("message"));
    }

    @Test
    void changeRole_retorna200() {
        when(adminUserUseCase.changeRole(any(), any(), any())).thenReturn(buildSummary());
        ResponseEntity<UserSummaryDTO> result = adminController.changeRole(mockAuth(), userId, mock(ChangeRoleRequestDTO.class));
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
