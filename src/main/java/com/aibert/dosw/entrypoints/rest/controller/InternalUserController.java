package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.UserAuthUpdateDTO;
import com.aibert.dosw.application.dto.response.UserAuthDTO;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
@Tag(name = "Internal", description = "Manage the internal service communication: user data retrieval and authentication updates for auth-service integration")
public class InternalUserController {

    private final UserRepositoryPort userRepository;

    @Operation(
            summary = "Get user by email",
            description = "Internal endpoint for auth-service to retrieve user authentication data by email. This endpoint is not protected by JWT as it's for inter-service communication."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found and returned successfully"),
            @ApiResponse(responseCode = "404", description = "User not found with the provided email")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserAuthDTO> findByEmail(
            @Parameter(
                description = "Email address of the user to retrieve",
                example = "n.parrado@mail.escuelaing.edu.co"
            ) @PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok(toAuthDTO(user));
    }

    @Operation(
            summary = "Get user by ID",
            description = "Internal endpoint for auth-service to retrieve user authentication data by UUID. This endpoint is not protected by JWT as it's for inter-service communication."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found and returned successfully"),
            @ApiResponse(responseCode = "404", description = "User not found with the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserAuthDTO> findById(
            @Parameter(
                description = "UUID of the user to retrieve",
                example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                schema = @Schema(type = "string", format = "uuid")
            ) @PathVariable UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok(toAuthDTO(user));
    }

    @Operation(
            summary = "Update authentication fields",
            description = "Internal endpoint for auth-service to update user authentication-related fields like failed login attempts and lock status. This endpoint is not protected by JWT as it's for inter-service communication."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User authentication fields updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found with the provided ID")
    })
    @PutMapping("/{id}/auth")
    public ResponseEntity<Void> updateAuthFields(
            @Parameter(
                description = "UUID of the user to update",
                example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                schema = @Schema(type = "string", format = "uuid")
            ) @PathVariable UUID id,
            @RequestBody UserAuthUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.save(User.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .verified(user.isVerified())
                .role(user.getRole())
                .status(user.getStatus())
                .career(user.getCareer())
                .doubleDegreeCareer(user.getDoubleDegreeCareer())
                .currentSemester(user.getCurrentSemester())
                .weeklyHours(user.getWeeklyHours())
                .dailyStudyHours(user.getDailyStudyHours())
                .currentGpa(user.getCurrentGpa())
                .currentSubjects(user.getCurrentSubjects())
                .academicGoal(user.getAcademicGoal())
                .currentlyWorking(user.isCurrentlyWorking())
                .availability(user.getAvailability())
                .profileComplete(user.isProfileComplete())
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .userName(user.getUserName())
                .passwordVersion(user.getPasswordVersion())
                .createdAt(user.getCreatedAt())
                .failedAttempts(dto.getFailedAttempts())
                .lockedUntil(dto.getLockedUntil())
                .build());

        return ResponseEntity.ok().build();
    }

    private UserAuthDTO toAuthDTO(User user) {
        return UserAuthDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .verified(user.isVerified())
                .role(user.getRole())
                .status(user.getStatus())
                .profileComplete(user.isProfileComplete())
                .failedAttempts(user.getFailedAttempts())
                .lockedUntil(user.getLockedUntil())
                .build();
    }
}
