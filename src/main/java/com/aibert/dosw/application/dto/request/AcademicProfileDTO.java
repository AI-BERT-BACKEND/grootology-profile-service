package com.aibert.dosw.application.dto.request;

import com.aibert.dosw.domain.model.user.AcademicGoal;
import com.aibert.dosw.domain.model.user.Availability;
import com.aibert.dosw.domain.model.user.Career;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "Request body for saving or updating the academic profile")
public class AcademicProfileDTO {

    @NotNull
    @Schema(description = "Primary career of the user", example = "SOFTWARE_ENGINEERING")
    private Career career;

    @Schema(description = "Second career if the user has a double degree program", example = "INDUSTRIAL_ENGINEERING", nullable = true)
    private Career doubleDegreeCareer;

    @NotNull
    @Min(1) @Max(10)
    @Schema(description = "Current semester (1 to 10)", example = "5")
    private Integer currentSemester;

    @NotNull
    @DecimalMin("0.0") @DecimalMax("5.0")
    @Schema(description = "Current GPA on a 0.0 to 5.0 scale", example = "3.8")
    private Double currentGpa;

    @NotNull
    @Size(min = 1, message = "Debes registrar al menos una materia")
    @Schema(description = "List of subjects the user is currently taking. At least one required.", example = "[\"Algorithms\", \"Databases\"]")
    private List<String> currentSubjects;

    @NotNull
    @Schema(description = "Main academic goal of the user", example = "GRADUATE_WITH_HONORS")
    private AcademicGoal academicGoal;

    @NotNull
    @Schema(description = "Whether the user is currently working", example = "false")
    private Boolean currentlyWorking;

    @NotNull
    @Schema(description = "User's availability for study sessions", example = "EVENINGS")
    private Availability availability;

    @NotNull
    @Min(1) @Max(12)
    @Schema(description = "Hours per day the user can dedicate to studying (1 to 12)", example = "3")
    private Integer dailyStudyHours;

    @NotNull
    @Min(1) @Max(80)
    @Schema(description = "Hours per week the user can dedicate to studying (1 to 80)", example = "15")
    private Integer weeklyHours;
}
