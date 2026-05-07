package com.aibert.dosw.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AcademicProfileResponseDTO {
    private String career;
    private Integer currentSemester;
    private Integer weeklyHours;
}
