package com.aibert.dosw.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "Request body for updating the personal profile")
public class UpdateProfileDTO {

    @Size(min = 3, max = 30, message = "El nombre de usuario debe tener entre 3 y 30 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "El nombre de usuario solo puede contener letras, números, puntos y guiones bajos")
    @Schema(description = "New username. Between 3 and 30 characters. Only letters, numbers, dots, and underscores allowed.", example = "nicolas.parrado")
    private String userName;
}
