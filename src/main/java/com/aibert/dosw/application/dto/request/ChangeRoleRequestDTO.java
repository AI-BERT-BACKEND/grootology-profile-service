package com.aibert.dosw.application.dto.request;

import com.aibert.dosw.domain.model.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "Request body for changing a user's role")
public class ChangeRoleRequestDTO {

    @NotNull
    @Schema(description = "New role to assign to the user", example = "ROLE_ADMIN", allowableValues = {"ROLE_USER", "ROLE_ADMIN"})
    private Role newRole;
}
