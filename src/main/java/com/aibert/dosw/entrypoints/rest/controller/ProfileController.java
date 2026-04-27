package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.AcademicProfileDTO;
import com.aibert.dosw.application.dto.request.PasswordChangeDTO;
import com.aibert.dosw.application.dto.request.UpdateProfileDTO;
import com.aibert.dosw.domain.ports.in.AcademicProfileUseCase;
import com.aibert.dosw.domain.ports.in.UpdateProfileUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final AcademicProfileUseCase academicProfileUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;

    @PutMapping("/{userId}/academic")
    public ResponseEntity<Map<String, String>> saveAcademicProfile(
            @PathVariable Long userId,
            @Valid @RequestBody AcademicProfileDTO dto) {
        academicProfileUseCase.saveAcademicProfile(userId, dto);
        return ResponseEntity.ok(Map.of("message", "Perfil académico guardado exitosamente."));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, String>> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestPart(value = "data") UpdateProfileDTO dto,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        updateProfileUseCase.updateProfile(userId, dto, photo);
        return ResponseEntity.ok(Map.of("message", "Perfil actualizado exitosamente."));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Map<String, String>> changePassword(
            @PathVariable Long userId,
            @Valid @RequestBody PasswordChangeDTO dto) {
        updateProfileUseCase.changePassword(userId, dto);
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada exitosamente."));
    }
}
