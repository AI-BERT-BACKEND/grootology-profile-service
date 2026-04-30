package com.aibert.dosw.domain.ports.in;

import com.aibert.dosw.application.dto.request.PasswordChangeDTO;
import com.aibert.dosw.application.dto.request.UpdateProfileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UpdateProfileUseCase {
    void updateProfile(UUID userId, UpdateProfileDTO dto, MultipartFile photo);
    void changePassword(UUID userId, PasswordChangeDTO dto);
}
