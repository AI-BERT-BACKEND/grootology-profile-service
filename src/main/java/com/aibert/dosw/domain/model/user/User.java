package com.aibert.dosw.domain.model.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class User {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private boolean verified;
    private boolean termsAccepted;
    private String career;
    private Integer currentSemester;
    private Integer weeklyHours;
    private String profilePhotoUrl;
    private Integer passwordVersion;
    private LocalDateTime createdAt;
}
