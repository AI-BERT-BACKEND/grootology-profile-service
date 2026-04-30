package com.aibert.dosw.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class RegisterResponseDTO {
    private UUID id;
    private String role;
    private String message;
}
