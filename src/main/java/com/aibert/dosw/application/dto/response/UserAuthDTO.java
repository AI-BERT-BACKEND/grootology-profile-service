package com.aibert.dosw.application.dto.response;

import com.aibert.dosw.domain.model.user.Role;
import com.aibert.dosw.domain.model.user.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@Schema(description = "Response body for user authentication data (internal use)")
public class UserAuthDTO {
    
    @Schema(description = "Unique identifier of the user", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;
    
    @Schema(description = "Full name of the user", example = "Nicolas Parrado")
    private String fullName;
    
    @Schema(description = "Institutional email address", example = "n.parrado@mail.escuelaing.edu.co")
    private String email;
    
    @Schema(description = "Encrypted password hash", example = "$2a$10$encrypted.password.hash")
    private String password;
    
    @Schema(description = "Whether the email has been verified", example = "true")
    private boolean verified;
    
    @Schema(description = "User role in the system", example = "ROLE_USER")
    private Role role;
    
    @Schema(description = "Current account status", example = "ACTIVE")
    private UserStatus status;
    
    @Schema(description = "Whether the user profile is complete", example = "true")
    private boolean profileComplete;
    
    @Schema(description = "Number of failed login attempts", example = "0")
    private Integer failedAttempts;
    
    @Schema(description = "Timestamp when account will be unlocked", example = "2026-05-22T16:52:39.763Z", nullable = true)
    private LocalDateTime lockedUntil;
}
