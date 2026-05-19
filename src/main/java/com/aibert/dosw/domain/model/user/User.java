package com.aibert.dosw.domain.model.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class User {
    private UUID id;
    private String fullName;
    private String email;
    private String password;
    private boolean verified;
    private Role role;
    private UserStatus status;
    private Career career;
    private Career doubleDegreeCareer;
    private Integer currentSemester;
    private Integer weeklyHours;
    private Integer dailyStudyHours;
    private Double currentGpa;
    private List<String> currentSubjects;
    private AcademicGoal academicGoal;
    private boolean currentlyWorking;
    private Availability availability;
    private boolean profileComplete;
    private String profilePhotoUrl;
    private String userName;
    private Integer passwordVersion;
    private LocalDateTime createdAt;
    private Integer failedAttempts;
    private LocalDateTime lockedUntil;
}
