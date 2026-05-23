package com.aibert.dosw.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Schema(description = "Request body for updating user authentication fields (internal use)")
public class UserAuthUpdateDTO {
    
    @Schema(description = "Number of failed login attempts", example = "2")
    private Integer failedAttempts;
    
    @Schema(description = "Timestamp when account will be unlocked", example = "2026-05-22T16:52:39.763Z", nullable = true)
    private LocalDateTime lockedUntil;
}
