package com.aibert.dosw.application.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateProfileDTO {

    @Size(min = 3, max = 100)
    private String fullName;
}
