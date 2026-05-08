package com.aibert.dosw.application.dto.request;

import com.aibert.dosw.domain.model.user.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangeRoleRequestDTO {
    @NotNull
    private Role newRole;
}
