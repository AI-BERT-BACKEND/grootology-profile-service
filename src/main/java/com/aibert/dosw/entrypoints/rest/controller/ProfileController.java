package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.AcademicProfileDTO;
import com.aibert.dosw.application.dto.request.PasswordChangeDTO;
import com.aibert.dosw.application.dto.request.UpdateProfileDTO;
import com.aibert.dosw.application.dto.response.AcademicProfileResponseDTO;
import com.aibert.dosw.domain.ports.in.AcademicProfileUseCase;
import com.aibert.dosw.domain.ports.in.DeleteAccountUseCase;
import com.aibert.dosw.domain.ports.in.UpdateProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "Manage the user profile data: personal info, academic profile, password change, and account deletion (R04, R05, R06, R07, R08)")
public class ProfileController {

    private final AcademicProfileUseCase academicProfileUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    @Operation(
            summary = "Get academic profile",
            description = "Retrieves the academic information of a user, including career, semester, GPA, current subjects, academic goals, availability, and study hours.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Academic profile retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Valid JWT token required."),
            @ApiResponse(responseCode = "404", description = "User or academic profile not found.")
    })
    @GetMapping("/{userId}/academic")
    public ResponseEntity<AcademicProfileResponseDTO> getAcademicProfile(
            @Parameter(
                description = "UUID of the user whose academic profile will be retrieved",
                example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                schema = @Schema(type = "string", format = "uuid")
            ) @PathVariable UUID userId) {
        return ResponseEntity.ok(academicProfileUseCase.getAcademicProfile(userId));
    }

    @Operation(
            summary = "Save or update academic profile",
            description = "Stores or updates the academic information of a user, including career, semester, GPA, current subjects, academic goals, availability, and study hours. This data is used by other services such as Gamification to personalize the experience.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Academic profile saved successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid or missing fields."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Valid JWT token required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @GetMapping("/{userId}/academic")
    public ResponseEntity<AcademicProfileResponseDTO> getAcademicProfile(
            @PathVariable UUID userId) {
        return ResponseEntity.ok(academicProfileUseCase.getAcademicProfile(userId));
    }

    @PutMapping("/{userId}/academic")
    public ResponseEntity<AcademicProfileResponseDTO> saveAcademicProfile(
            @Parameter(
                description = "UUID of the user whose academic profile will be updated",
                example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                schema = @Schema(type = "string", format = "uuid")
            ) @PathVariable UUID userId,
            @Valid @RequestBody AcademicProfileDTO dto) {
        return ResponseEntity.ok(academicProfileUseCase.saveAcademicProfile(userId, dto));
    }

    @Operation(
            summary = "Update personal profile",
            description = "Updates the username of the authenticated user. The username must be between 3 and 30 characters and can only contain letters, numbers, dots, and underscores.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid username format."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Valid JWT token required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, String>> updateProfile(
            @Parameter(
                description = "UUID of the user to update",
                example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                schema = @Schema(type = "string", format = "uuid")
            ) @PathVariable UUID userId,
            @Valid @RequestBody UpdateProfileDTO dto) {
        updateProfileUseCase.updateProfile(userId, dto, null);
        return ResponseEntity.ok(Map.of("message", "Perfil actualizado exitosamente."));
    }

    @Operation(
            summary = "Update profile photo",
            description = "Uploads and updates the profile photo of the user. The request must be sent as multipart/form-data with the image file in the 'photo' field.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Photo updated successfully."),
            @ApiResponse(responseCode = "400", description = "Missing or invalid file."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Valid JWT token required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @PutMapping(value = "/{userId}/photo", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> updateProfilePhoto(
            @Parameter(
                description = "UUID of the user to update",
                example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                schema = @Schema(type = "string", format = "uuid")
            ) @PathVariable UUID userId,
            @Parameter(description = "Image file to set as profile photo") @RequestPart(value = "photo") MultipartFile photo) {
        updateProfileUseCase.updateProfile(userId, new UpdateProfileDTO(), photo);
        return ResponseEntity.ok(Map.of("message", "Foto actualizada exitosamente."));
    }

    @Operation(
            summary = "Change password",
            description = "Changes the password of the authenticated user. Requires the current password for verification. The new password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one number. All active sessions are invalidated after the change.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully. All sessions invalidated."),
            @ApiResponse(responseCode = "400", description = "Passwords do not match or do not meet security requirements."),
            @ApiResponse(responseCode = "401", description = "Current password is incorrect."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @PutMapping("/{userId}/password")
    public ResponseEntity<Map<String, String>> changePassword(
            @Parameter(
                description = "UUID of the user changing the password",
                example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                schema = @Schema(type = "string", format = "uuid")
            ) @PathVariable UUID userId,
            @Valid @RequestBody PasswordChangeDTO dto) {
        updateProfileUseCase.changePassword(userId, dto);
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada. Todas las sesiones han sido cerradas."));
    }

    @Operation(
            summary = "Delete account",
            description = "Permanently deletes the user's account. Requires the current password as confirmation. This action is irreversible and will invalidate all active sessions.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Incorrect password."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteAccount(
            @Parameter(
                description = "UUID of the user to delete",
                example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                schema = @Schema(type = "string", format = "uuid")
            ) @PathVariable UUID userId,
            @Parameter(description = "Current password for confirmation") @RequestParam String currentPassword) {
        deleteAccountUseCase.deleteAccount(userId, currentPassword);
        return ResponseEntity.ok(Map.of("accountDeleted", true, "sessionInvalidated", true, "redirectLogin", true));
    }
}
