package com.aibert.dosw.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Response body for OTP verification attempt")
public class OtpVerificationResponseDTO {
    
    @Schema(description = "Whether the OTP verification was successful", example = "true")
    private boolean verificationStatus;
    
    @Schema(description = "Whether the account is now active", example = "true")
    private boolean accountStatus;
    
    @Schema(description = "Seconds remaining for OTP expiry or unlock time", example = "247")
    private long expirationTime;
    
    @Schema(description = "Whether a new OTP can be requested", example = "false")
    private boolean resendAvailability;
}
