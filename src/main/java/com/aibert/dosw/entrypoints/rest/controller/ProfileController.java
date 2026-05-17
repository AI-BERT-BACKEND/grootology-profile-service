package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.AcademicProfileDTO;
import com.aibert.dosw.application.dto.request.PasswordChangeDTO;
import com.aibert.dosw.application.dto.request.UpdateProfileDTO;
import com.aibert.dosw.application.dto.response.AcademicProfileResponseDTO;
import com.aibert.dosw.domain.ports.in.AcademicProfileUseCase;
import com.aibert.dosw.domain.ports.in.DeleteAccountUseCase;
import com.aibert.dosw.domain.ports.in.UpdateProfileUseCase;
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
public class ProfileController {

    private final AcademicProfileUseCase academicProfileUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    @PutMapping("/{userId}/academic")
    public ResponseEntity<AcademicProfileResponseDTO> saveAcademicProfile(
            @PathVariable UUID userId,
            @Valid @RequestBody AcademicProfileDTO dto) {
        return ResponseEntity.ok(academicProfileUseCase.saveAcademicProfile(userId, dto));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, String>> updateProfile(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateProfileDTO dto) {
        updateProfileUseCase.updateProfile(userId, dto, null);
        return ResponseEntity.ok(Map.of("message", "Perfil actualizado exitosamente."));
    }

    @PutMapping(value = "/{userId}/photo", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> updateProfilePhoto(
            @PathVariable UUID userId,
            @RequestPart(value = "photo") MultipartFile photo) {
        updateProfileUseCase.updateProfile(userId, new UpdateProfileDTO(), photo);
        return ResponseEntity.ok(Map.of("message", "Foto actualizada exitosamente."));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Map<String, String>> changePassword(
            @PathVariable UUID userId,
            @Valid @RequestBody PasswordChangeDTO dto) {
        updateProfileUseCase.changePassword(userId, dto);
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada. Todas las sesiones han sido cerradas."));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteAccount(
            @PathVariable UUID userId,
            @RequestParam String currentPassword) {
        deleteAccountUseCase.deleteAccount(userId, currentPassword);
        return ResponseEntity.ok(Map.of("accountDeleted", true, "sessionInvalidated", true, "redirectLogin", true));
    }
}
