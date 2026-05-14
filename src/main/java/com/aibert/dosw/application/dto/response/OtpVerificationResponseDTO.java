package com.aibert.dosw.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OtpVerificationResponseDTO {
    private boolean verificationStatus;
    private boolean accountStatus;
    private long expirationTime;
    private boolean resendAvailability;
}
