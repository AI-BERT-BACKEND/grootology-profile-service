package com.aibert.dosw.domain.ports.in;

import com.aibert.dosw.application.dto.request.RegisterRequestDTO;
import com.aibert.dosw.application.dto.response.OtpVerificationResponseDTO;
import com.aibert.dosw.application.dto.response.RegisterResponseDTO;
import java.util.UUID;

public interface RegisterUseCase {
    RegisterResponseDTO register(RegisterRequestDTO request);
    void verifyEmail(String token);
    void resendVerificationEmail(String email);
    OtpVerificationResponseDTO verifyOtp(UUID userId, String otp);
}
