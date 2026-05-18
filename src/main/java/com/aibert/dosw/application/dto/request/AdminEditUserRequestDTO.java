package com.aibert.dosw.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
@Schema(description = "Request body for admin to edit a user's basic information")
public class AdminEditUserRequestDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+$", message = "Solo letras, números y espacios")
    @Schema(description = "Updated full name of the user", example = "Nicolas Parrado")
    private String fullName;

    @NotBlank
    @Email
    @Pattern(regexp = "^[^@]+@mail\\.escuelaing\\.edu\\.co$", message = "Debe ser correo institucional")
    @Schema(description = "Updated institutional email. Must end with @mail.escuelaing.edu.co", example = "n.parrado@mail.escuelaing.edu.co")
    private String email;
}
