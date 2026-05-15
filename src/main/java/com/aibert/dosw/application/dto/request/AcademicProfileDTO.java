package com.aibert.dosw.application.dto.request;

import com.aibert.dosw.domain.model.user.AcademicGoal;
import com.aibert.dosw.domain.model.user.Availability;
import com.aibert.dosw.domain.model.user.Career;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class AcademicProfileDTO {

    @NotNull
    private Career career;

    private Career doubleDegreeCareer;

    @NotNull
    @Min(1) @Max(10)
    private Integer currentSemester;

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

    @NotNull
    private Availability availability;

    @NotNull
    @Min(1) @Max(12)
    private Integer dailyStudyHours;

    @NotNull
    @Min(1) @Max(80)
    private Integer weeklyHours;
}
