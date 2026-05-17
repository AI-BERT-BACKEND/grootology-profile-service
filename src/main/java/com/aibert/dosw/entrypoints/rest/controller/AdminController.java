package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.AdminEditUserRequestDTO;
import com.aibert.dosw.application.dto.request.ChangeRoleRequestDTO;
import com.aibert.dosw.application.dto.response.UserSummaryDTO;
import com.aibert.dosw.domain.ports.in.AdminUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final AdminUserUseCase adminUserUseCase;

    @GetMapping
    public ResponseEntity<List<UserSummaryDTO>> listUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String role) {
        return ResponseEntity.ok(adminUserUseCase.listUsers(name, email, status, role));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserSummaryDTO> getUserDetail(@PathVariable UUID userId) {
        return ResponseEntity.ok(adminUserUseCase.getUserDetail(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserSummaryDTO> editUser(
            Authentication auth,
            @PathVariable UUID userId,
            @Valid @RequestBody AdminEditUserRequestDTO request) {
        UUID adminId = UUID.fromString((String) auth.getCredentials());
        return ResponseEntity.ok(adminUserUseCase.editUser(adminId, userId, request));
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<UserSummaryDTO> updateStatus(
            Authentication auth,
            @PathVariable UUID userId,
            @RequestParam String status) {
        UUID adminId = UUID.fromString((String) auth.getCredentials());
        return ResponseEntity.ok(adminUserUseCase.updateUserStatus(adminId, userId, status));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(
            Authentication auth,
            @PathVariable UUID userId) {
        UUID adminId = UUID.fromString((String) auth.getCredentials());
        adminUserUseCase.deleteUser(adminId, userId);
        return ResponseEntity.ok(Map.of("message", "Usuario eliminado exitosamente"));
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<UserSummaryDTO> changeRole(
            Authentication auth,
            @PathVariable UUID userId,
            @Valid @RequestBody ChangeRoleRequestDTO request) {
        UUID adminId = UUID.fromString((String) auth.getCredentials());
        return ResponseEntity.ok(adminUserUseCase.changeRole(adminId, userId, request));
    }
}
