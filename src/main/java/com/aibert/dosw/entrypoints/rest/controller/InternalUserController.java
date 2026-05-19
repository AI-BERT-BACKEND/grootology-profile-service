package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.UserAuthUpdateDTO;
import com.aibert.dosw.application.dto.response.UserAuthDTO;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserRepositoryPort userRepository;

    @GetMapping("/email/{email}")
    public ResponseEntity<UserAuthDTO> findByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok(toAuthDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAuthDTO> findById(@PathVariable UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok(toAuthDTO(user));
    }

    @PutMapping("/{id}/auth")
    public ResponseEntity<Void> updateAuthFields(@PathVariable UUID id,
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
