package com.aibert.dosw.application.dto.request;

import com.aibert.dosw.domain.model.user.AcademicGoal;
import com.aibert.dosw.domain.model.user.Career;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class AcademicProfileDTO {

    private Career career;

    @Min(1) @Max(10)
    private Integer currentSemester;

    @NotNull
    @Min(1) @Max(80)
    private Integer weeklyHours;

    @NotNull
    @DecimalMin("0.0") @DecimalMax("5.0")
    private Double currentGpa;

    @NotNull
    @Min(1) @Max(10)
    private Integer currentSubjects;

    @NotNull
    private AcademicGoal academicGoal;

    @NotNull
    private Boolean currentlyWorking;
}
