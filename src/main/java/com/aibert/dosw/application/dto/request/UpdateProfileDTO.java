package com.aibert.dosw.application.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateProfileDTO {

    @Size(min = 3, max = 30, message = "El nombre de usuario debe tener entre 3 y 30 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "El nombre de usuario solo puede contener letras, números, puntos y guiones bajos")
    private String userName;
}
