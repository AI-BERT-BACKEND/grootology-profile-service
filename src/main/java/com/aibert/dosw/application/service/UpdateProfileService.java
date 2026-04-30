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
            photoUrl = fileUploadService.upload(photo);
        }

        userRepository.save(User.builder()
                .id(user.getId())
                .fullName(dto.getFullName() != null ? dto.getFullName() : user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .verified(user.isVerified())
                .role(user.getRole())
                .career(user.getCareer())
                .currentSemester(user.getCurrentSemester())
                .weeklyHours(user.getWeeklyHours())
                .profilePhotoUrl(photoUrl)
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
                .career(user.getCareer())
                .currentSemester(user.getCurrentSemester())
                .weeklyHours(user.getWeeklyHours())
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .passwordVersion(user.getPasswordVersion() == null ? 1 : user.getPasswordVersion() + 1)
                .createdAt(user.getCreatedAt())
                .build());
    }
}
