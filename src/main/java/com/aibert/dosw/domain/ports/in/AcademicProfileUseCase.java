package com.aibert.dosw.domain.ports.in;

import com.aibert.dosw.application.dto.request.AcademicProfileDTO;
import com.aibert.dosw.application.dto.response.AcademicProfileResponseDTO;

import java.util.UUID;

public interface AcademicProfileUseCase {
    AcademicProfileResponseDTO saveAcademicProfile(UUID userId, AcademicProfileDTO dto);
}
