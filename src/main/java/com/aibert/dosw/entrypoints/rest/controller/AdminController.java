package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.AdminEditUserRequestDTO;
import com.aibert.dosw.application.dto.request.ChangeRoleRequestDTO;
import com.aibert.dosw.application.dto.response.UserSummaryDTO;
import com.aibert.dosw.domain.ports.in.AdminUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Admin", description = "Endpoints for administrator user management. All endpoints require ROLE_ADMIN.")
public class AdminController {

    private final AdminUserUseCase adminUserUseCase;

    @Operation(
            summary = "List all users",
            description = "Returns a list of all registered users. Supports optional filtering by name, email, status, and role. All filters can be combined."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User list returned successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Valid JWT token required."),
            @ApiResponse(responseCode = "403", description = "Access denied. ROLE_ADMIN required.")
    })
    @GetMapping
    public ResponseEntity<List<UserSummaryDTO>> listUsers(
            @Parameter(description = "Filter by name (partial match)") @RequestParam(required = false) String name,
            @Parameter(description = "Filter by email (partial match)") @RequestParam(required = false) String email,
            @Parameter(description = "Filter by account status (ACTIVE, INACTIVE, LOCKED)") @RequestParam(required = false) String status,
            @Parameter(description = "Filter by role (ROLE_USER, ROLE_ADMIN)") @RequestParam(required = false) String role) {
        return ResponseEntity.ok(adminUserUseCase.listUsers(name, email, status, role));
    }

    @Operation(
            summary = "Get user details",
            description = "Returns the full details of a specific user by their ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User details returned successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Valid JWT token required."),
            @ApiResponse(responseCode = "403", description = "Access denied. ROLE_ADMIN required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserSummaryDTO> getUserDetail(
            @Parameter(description = "ID of the user to retrieve") @PathVariable UUID userId) {
        return ResponseEntity.ok(adminUserUseCase.getUserDetail(userId));
    }

    @Operation(
            summary = "Edit user",
            description = "Allows an admin to update the full name and institutional email of a user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid fields in the request body."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Valid JWT token required."),
            @ApiResponse(responseCode = "403", description = "Access denied. ROLE_ADMIN required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<UserSummaryDTO> editUser(
            Authentication auth,
            @Parameter(description = "ID of the user to edit") @PathVariable UUID userId,
            @Valid @RequestBody AdminEditUserRequestDTO request) {
        UUID adminId = UUID.fromString((String) auth.getCredentials());
        return ResponseEntity.ok(adminUserUseCase.editUser(adminId, userId, request));
    }

    @Operation(
            summary = "Update user status",
            description = "Changes the account status of a user. Accepted values: ACTIVE, INACTIVE, LOCKED."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User status updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid status value."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Valid JWT token required."),
            @ApiResponse(responseCode = "403", description = "Access denied. ROLE_ADMIN required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @PutMapping("/{userId}/status")
    public ResponseEntity<UserSummaryDTO> updateStatus(
            Authentication auth,
            @Parameter(description = "ID of the user to update") @PathVariable UUID userId,
            @Parameter(description = "New status: ACTIVE, INACTIVE, or LOCKED") @RequestParam String status) {
        UUID adminId = UUID.fromString((String) auth.getCredentials());
        return ResponseEntity.ok(adminUserUseCase.updateUserStatus(adminId, userId, status));
    }

    @Operation(
            summary = "Delete user",
            description = "Permanently deletes a user account from the system. This action is irreversible."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Valid JWT token required."),
            @ApiResponse(responseCode = "403", description = "Access denied. ROLE_ADMIN required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(
            Authentication auth,
            @Parameter(description = "ID of the user to delete") @PathVariable UUID userId) {
        UUID adminId = UUID.fromString((String) auth.getCredentials());
        adminUserUseCase.deleteUser(adminId, userId);
        return ResponseEntity.ok(Map.of("message", "Usuario eliminado exitosamente"));
    }

    @Operation(
            summary = "Change user role",
            description = "Updates the role of a user. Accepted values: ROLE_USER, ROLE_ADMIN."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid role value."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Valid JWT token required."),
            @ApiResponse(responseCode = "403", description = "Access denied. ROLE_ADMIN required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @PutMapping("/{userId}/role")
    public ResponseEntity<UserSummaryDTO> changeRole(
            Authentication auth,
            @Parameter(description = "ID of the user whose role will be changed") @PathVariable UUID userId,
            @Valid @RequestBody ChangeRoleRequestDTO request) {
        UUID adminId = UUID.fromString((String) auth.getCredentials());
        return ResponseEntity.ok(adminUserUseCase.changeRole(adminId, userId, request));
    }
}
