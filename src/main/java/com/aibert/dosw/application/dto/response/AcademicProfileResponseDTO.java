package com.aibert.dosw.application.dto.response;

import com.aibert.dosw.domain.model.user.AcademicGoal;
import com.aibert.dosw.domain.model.user.Availability;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AcademicProfileResponseDTO {
    private String career;
    private String doubleDegreeCareer;
    private Integer currentSemester;
    private Integer weeklyHours;
    private Integer dailyStudyHours;
    private Double currentGpa;
    private Integer currentSubjects;
    private AcademicGoal academicGoal;
    private Boolean currentlyWorking;
    private Availability availability;
    private boolean profileComplete;
}
