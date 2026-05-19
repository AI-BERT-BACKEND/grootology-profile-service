package com.aibert.dosw.application.dto.response;

import com.aibert.dosw.domain.model.user.Role;
import com.aibert.dosw.domain.model.user.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserAuthDTO {
    private UUID id;
    private String email;
    private String password;
    private boolean verified;
    private Role role;
    private UserStatus status;
    private boolean profileComplete;
    private Integer failedAttempts;
    private LocalDateTime lockedUntil;
}
