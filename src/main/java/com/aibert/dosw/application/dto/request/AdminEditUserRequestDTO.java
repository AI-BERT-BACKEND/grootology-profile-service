package com.aibert.dosw.application.dto.request;

import com.aibert.dosw.domain.model.user.Role;
import com.aibert.dosw.domain.model.user.UserStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class AdminEditUserRequestDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "Solo letras y espacios")
    private String fullName;

    @NotBlank
    @Email
    @Pattern(regexp = "^[^@]+@mail\\.escuelaing\\.edu\\.co$", message = "Debe ser correo institucional")
    private String email;

    @NotNull
    private Role role;

    @NotNull
    private UserStatus status;
}
