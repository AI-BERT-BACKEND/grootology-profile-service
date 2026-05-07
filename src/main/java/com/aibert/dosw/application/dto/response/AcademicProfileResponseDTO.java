package com.aibert.dosw.application.dto.response;

import com.aibert.dosw.domain.model.user.AcademicGoal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AcademicProfileResponseDTO {
    private String career;
    private Integer currentSemester;
    private Integer weeklyHours;
    private Double currentGpa;
    private Integer currentSubjects;
    private AcademicGoal academicGoal;
    private Boolean currentlyWorking;
}
