package com.aibert.dosw.application.dto.response;

import com.aibert.dosw.domain.model.user.AcademicGoal;
import com.aibert.dosw.domain.model.user.Availability;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "Response body for academic profile data")
public class AcademicProfileResponseDTO {
    
    @Schema(description = "Primary career of the user", example = "SOFTWARE_ENGINEERING")
    private String career;
    
    @Schema(description = "Second career if user has double degree", example = "INDUSTRIAL_ENGINEERING", nullable = true)
    private String doubleDegreeCareer;
    
    @Schema(description = "Current semester (1 to 10)", example = "5")
    private Integer currentSemester;
    
    @Schema(description = "Hours per week available for studying", example = "15")
    private Integer weeklyHours;
    
    @Schema(description = "Hours per day dedicated to studying", example = "3")
    private Integer dailyStudyHours;
    
    @Schema(description = "Current GPA on 0.0 to 5.0 scale", example = "3.8")
    private Double currentGpa;
    
    @Schema(description = "List of subjects currently taking", example = "[\"Algorithms\", \"Databases\"]")
    private List<String> currentSubjects;
    
    @Schema(description = "Main academic goal", example = "GRADUATE_WITH_HONORS")
    private AcademicGoal academicGoal;
    
    @Schema(description = "Whether the user is currently working", example = "false")
    private Boolean currentlyWorking;
    
    @Schema(description = "User's availability for study sessions", example = "EVENINGS")
    private Availability availability;
    
    @Schema(description = "Whether the academic profile is complete", example = "true")
    private boolean profileComplete;
}
