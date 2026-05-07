package com.aibert.dosw.application.dto.response;

import com.aibert.dosw.domain.model.user.Role;
import com.aibert.dosw.domain.model.user.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserSummaryDTO {
    private UUID id;
    private String fullName;
    private String email;
    private Role role;
    private UserStatus status;
    private LocalDateTime createdAt;
}
