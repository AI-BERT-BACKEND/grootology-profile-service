package com.aibert.dosw.application.dto.request;

import com.aibert.dosw.domain.model.user.AcademicGoal;
import com.aibert.dosw.domain.model.user.Availability;
import com.aibert.dosw.domain.model.user.Career;
import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "Request body for saving or updating the academic profile")
public class AcademicProfileDTO {

    @Schema(description = "Second career if the user has a double degree program", example = "INDUSTRIAL_ENGINEERING", nullable = true)
    @JsonAlias("doubleDegreeCareer")
    private Career secondaryCareer;

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
    @JsonAlias("currentSubjects")
    private List<String> subjects;

    @NotNull
    @Schema(description = "Main academic goal of the user", example = "GRADUATE_WITH_HONORS")
    private AcademicGoal academicGoal;

    @NotNull
    @Schema(description = "Whether the user is currently working", example = "false")
    @JsonAlias("currentlyWorking")
    private Boolean currentlyEmployed;

    @NotNull
    @Schema(description = "User's availability for study sessions", example = "EVENINGS")
    @JsonAlias("availability")
    private Availability studyAvailability;

    @NotNull
    @Min(1) @Max(12)
    @Schema(description = "Hours per day the user can dedicate to studying (1 to 12)", example = "3")
    private Integer dailyStudyHours;
}
