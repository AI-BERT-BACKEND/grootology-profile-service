package com.aibert.dosw.domain.ports.in;

import com.aibert.dosw.application.dto.request.AcademicProfileDTO;
import com.aibert.dosw.application.dto.response.AcademicProfileResponseDTO;

public interface AcademicProfileUseCase {
    AcademicProfileResponseDTO saveAcademicProfile(Long userId, AcademicProfileDTO dto);
}
