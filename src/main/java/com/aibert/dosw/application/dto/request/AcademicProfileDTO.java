package com.aibert.dosw.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class AcademicProfileDTO {

    @NotBlank
    @Size(max = 100)
    private String career;

    @NotNull
    @Min(1) @Max(12)
    private Integer currentSemester;

    @NotNull
    @Min(1) @Max(80)
    private Integer weeklyHours;
}
