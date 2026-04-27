package com.aibert.dosw.domain.ports.in;

import com.aibert.dosw.application.dto.request.AcademicProfileDTO;

public interface AcademicProfileUseCase {
    void saveAcademicProfile(Long userId, AcademicProfileDTO dto);
}
