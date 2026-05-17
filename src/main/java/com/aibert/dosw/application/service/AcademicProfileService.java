package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.request.AcademicProfileDTO;
import com.aibert.dosw.application.dto.response.AcademicProfileResponseDTO;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.domain.ports.in.AcademicProfileUseCase;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AcademicProfileService implements AcademicProfileUseCase {

    private final UserRepositoryPort userRepository;

    @Override
    public AcademicProfileResponseDTO saveAcademicProfile(UUID userId, AcademicProfileDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (dto.getDoubleDegreeCareer() != null && dto.getDoubleDegreeCareer().equals(dto.getCareer())) {
            throw new IllegalArgumentException("La doble carrera no puede ser igual a la carrera principal");
        }

        userRepository.save(User.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .verified(user.isVerified())
                .role(user.getRole())
                .status(user.getStatus())
                .career(dto.getCareer())
                .doubleDegreeCareer(dto.getDoubleDegreeCareer())
                .currentSemester(dto.getCurrentSemester())
                .weeklyHours(dto.getWeeklyHours())
                .dailyStudyHours(dto.getDailyStudyHours())
                .currentGpa(dto.getCurrentGpa())
                .currentSubjects(dto.getCurrentSubjects())
                .academicGoal(dto.getAcademicGoal())
                .currentlyWorking(dto.getCurrentlyWorking())
                .availability(dto.getAvailability())
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .profileComplete(true)
                .passwordVersion(user.getPasswordVersion())
                .createdAt(user.getCreatedAt())
                .build());

        return AcademicProfileResponseDTO.builder()
                .career(dto.getCareer().name())
                .doubleDegreeCareer(dto.getDoubleDegreeCareer() != null ? dto.getDoubleDegreeCareer().name() : null)
                .currentSemester(dto.getCurrentSemester())
                .weeklyHours(dto.getWeeklyHours())
                .dailyStudyHours(dto.getDailyStudyHours())
                .currentGpa(dto.getCurrentGpa())
                .currentSubjects(dto.getCurrentSubjects())
                .academicGoal(dto.getAcademicGoal())
                .currentlyWorking(dto.getCurrentlyWorking())
                .availability(dto.getAvailability())
                .profileComplete(true)
                .build();
    }
}
