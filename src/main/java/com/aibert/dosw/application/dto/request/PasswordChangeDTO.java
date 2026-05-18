package com.aibert.dosw.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "Request body for changing the user's password")
public class PasswordChangeDTO {

    @NotBlank
    @Schema(description = "Current password of the user for verification", example = "OldPassword1")
    private String currentPassword;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$", message = "Mínimo 8 caracteres, una mayúscula, una minúscula y un número")
    @Schema(description = "New password. Minimum 8 characters, at least one uppercase letter, one lowercase letter, and one number.", example = "NewPassword1")
    private String newPassword;

    @NotBlank
    @Schema(description = "Must match the newPassword field exactly", example = "NewPassword1")
    private String confirmNewPassword;
}
