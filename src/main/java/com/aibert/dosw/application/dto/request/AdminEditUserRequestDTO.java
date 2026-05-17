package com.aibert.dosw.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class AdminEditUserRequestDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+$", message = "Solo letras, números y espacios")
    private String fullName;

    @NotBlank
    @Email
    @Pattern(regexp = "^[^@]+@mail\\.escuelaing\\.edu\\.co$", message = "Debe ser correo institucional")
    private String email;
}
