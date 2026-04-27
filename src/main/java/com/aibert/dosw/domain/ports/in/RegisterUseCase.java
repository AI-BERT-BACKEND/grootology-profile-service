package com.aibert.dosw.domain.ports.in;

import com.aibert.dosw.application.dto.request.RegisterRequestDTO;

public interface RegisterUseCase {
    void register(RegisterRequestDTO request);
    void verifyEmail(String token);
    void resendVerificationEmail(String email);
}
