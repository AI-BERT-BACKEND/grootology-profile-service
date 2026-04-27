package com.aibert.dosw.domain.ports.in;

import com.aibert.dosw.application.dto.request.PasswordChangeDTO;
import com.aibert.dosw.application.dto.request.UpdateProfileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProfileUseCase {
    void updateProfile(Long userId, UpdateProfileDTO dto, MultipartFile photo);
    void changePassword(Long userId, PasswordChangeDTO dto);
}
