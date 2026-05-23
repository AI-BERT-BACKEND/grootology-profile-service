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
@Schema(description = "Response body for user summary information")
public class UserSummaryDTO {
    
    @Schema(description = "Unique identifier of the user", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;
    
    @Schema(description = "Full name of the user", example = "Nicolas Parrado")
    private String fullName;
    
    @Schema(description = "Institutional email address", example = "n.parrado@mail.escuelaing.edu.co")
    private String email;
    
    @Schema(description = "User role in the system", example = "ROLE_USER")
    private Role role;
    
    @Schema(description = "Current account status", example = "ACTIVE")
    private UserStatus status;
    
    @Schema(description = "Account creation timestamp", example = "2026-05-22T16:52:39.763Z")
    private LocalDateTime createdAt;
}
