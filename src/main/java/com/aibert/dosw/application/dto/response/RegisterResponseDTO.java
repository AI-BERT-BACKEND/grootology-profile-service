package com.aibert.dosw.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@Schema(description = "Response body for successful user registration")
public class RegisterResponseDTO {
    
    @Schema(description = "Unique identifier of the newly registered user", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;
    
    @Schema(description = "Role assigned to the user upon registration", example = "ESTUDIANTE")
    private String role;
    
    @Schema(description = "Confirmation message with next steps", example = "Registro exitoso. Revisa tu correo para verificar tu cuenta.")
    private String message;
}
