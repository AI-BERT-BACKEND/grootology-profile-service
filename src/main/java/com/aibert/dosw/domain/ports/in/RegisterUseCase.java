package com.aibert.dosw.domain.ports.in;

import com.aibert.dosw.application.dto.request.RegisterRequestDTO;
import com.aibert.dosw.application.dto.response.RegisterResponseDTO;

public interface RegisterUseCase {
    RegisterResponseDTO register(RegisterRequestDTO request);
    void verifyEmail(String token);
    void resendVerificationEmail(String email);
}
