package com.aibert.dosw.application.dto.request;

import com.aibert.dosw.domain.model.user.Career;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
@Schema(description = "Request body for user registration")
public class RegisterRequestDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "Solo letras y espacios")
    @Schema(description = "Full name of the user. Only letters and spaces allowed.", example = "Nicolas Parrado")
    private String fullName;

    @NotBlank
    @Email
    @Pattern(regexp = "^[^@]+@mail\\.escuelaing\\.edu\\.co$", message = "Debe ser un correo institucional @mail.escuelaing.edu.co")
    @Schema(description = "Institutional email address. Must end with @mail.escuelaing.edu.co", example = "n.parrado@mail.escuelaing.edu.co")
    private String email;

    @NotNull
    @Schema(description = "Career the user is enrolled in", example = "SOFTWARE_ENGINEERING")
    private Career career;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$", message = "Mínimo 8 caracteres, una mayúscula, una minúscula y un número")
    @Schema(description = "Password. Minimum 8 characters, at least one uppercase letter, one lowercase letter, and one number.", example = "MyPassword1")
    private String password;

    @NotBlank
    @Schema(description = "Must match the password field exactly", example = "MyPassword1")
    private String confirmPassword;
}

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
