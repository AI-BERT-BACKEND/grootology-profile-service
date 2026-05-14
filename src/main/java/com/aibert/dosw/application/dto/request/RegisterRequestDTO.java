package com.aibert.dosw.application.dto.request;

import com.aibert.dosw.domain.model.user.Career;
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

    @NotNull
    private Career career;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$", message = "Mínimo 8 caracteres, una mayúscula, una minúscula y un número")
    private String password;

    @NotBlank
    private String confirmPassword;
}
