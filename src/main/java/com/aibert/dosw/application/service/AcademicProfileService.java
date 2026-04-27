package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.request.AcademicProfileDTO;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.domain.ports.in.AcademicProfileUseCase;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcademicProfileService implements AcademicProfileUseCase {

    private final UserRepositoryPort userRepository;

    @Override
    public void saveAcademicProfile(Long userId, AcademicProfileDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        userRepository.save(User.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .verified(user.isVerified())
                .termsAccepted(user.isTermsAccepted())
                .career(dto.getCareer())
                .currentSemester(dto.getCurrentSemester())
                .weeklyHours(dto.getWeeklyHours())
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .createdAt(user.getCreatedAt())
                .build());
    }
}
