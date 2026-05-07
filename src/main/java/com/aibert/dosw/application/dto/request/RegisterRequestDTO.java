package com.aibert.dosw.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class RegisterRequestDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "Solo letras y espacios")
    private String fullName;

    @NotBlank
    @Email
    @Pattern(regexp = "^[^@]+@mail\\.escuelaing\\.edu\\.co$", message = "Debe ser un correo institucional @mail.escuelaing.edu.co")
    private String email;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Mínimo 8 caracteres, letras y números")
    private String password;

    @NotBlank
    private String confirmPassword;
}
