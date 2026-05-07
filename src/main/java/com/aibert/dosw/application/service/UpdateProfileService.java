package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.request.PasswordChangeDTO;
import com.aibert.dosw.application.dto.request.UpdateProfileDTO;
import com.aibert.dosw.domain.exceptions.InvalidPasswordException;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.domain.ports.in.UpdateProfileUseCase;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateProfileService implements UpdateProfileUseCase {

    private final UserRepositoryPort userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FileUploadService fileUploadService;

    @Override
    public void updateProfile(UUID userId, UpdateProfileDTO dto, MultipartFile photo) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        String photoUrl = user.getProfilePhotoUrl();
        if (photo != null && !photo.isEmpty()) {
            String contentType = photo.getContentType();
            if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
                throw new IllegalArgumentException("Solo se aceptan archivos JPG o PNG");
            }
            photoUrl = fileUploadService.upload(photo);
        }

        userRepository.save(User.builder()
                .id(user.getId())
                .fullName(dto.getFullName() != null ? dto.getFullName() : user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .verified(user.isVerified())
                .role(user.getRole())
                .status(user.getStatus())
                .career(user.getCareer())
                .currentSemester(user.getCurrentSemester())
                .weeklyHours(user.getWeeklyHours())
                .currentGpa(user.getCurrentGpa())
                .currentSubjects(user.getCurrentSubjects())
                .academicGoal(user.getAcademicGoal())
                .currentlyWorking(user.isCurrentlyWorking())
                .profileComplete(user.isProfileComplete())
                .profilePhotoUrl(photoUrl)
                .passwordVersion(user.getPasswordVersion())
                .createdAt(user.getCreatedAt())
                .build());
    }

    @Override
    public void changePassword(UUID userId, PasswordChangeDTO dto) {
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        userRepository.save(User.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(dto.getNewPassword()))
                .verified(user.isVerified())
                .role(user.getRole())
                .status(user.getStatus())
                .career(user.getCareer())
                .currentSemester(user.getCurrentSemester())
                .weeklyHours(user.getWeeklyHours())
                .currentGpa(user.getCurrentGpa())
                .currentSubjects(user.getCurrentSubjects())
                .academicGoal(user.getAcademicGoal())
                .currentlyWorking(user.isCurrentlyWorking())
                .profileComplete(user.isProfileComplete())
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .passwordVersion(user.getPasswordVersion() == null ? 1 : user.getPasswordVersion() + 1)
                .createdAt(user.getCreatedAt())
                .build());
    }
}
