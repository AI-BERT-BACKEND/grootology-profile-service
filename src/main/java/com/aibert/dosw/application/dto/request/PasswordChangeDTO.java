package com.aibert.dosw.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PasswordChangeDTO {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$", message = "Mínimo 8 caracteres, una mayúscula, una minúscula y un número")
    private String newPassword;

    @NotBlank
    private String confirmNewPassword;
}
