package com.aibert.dosw.infrastructure.adapters.persistence.entity;

import com.aibert.dosw.domain.model.user.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean verified;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private Career career;

    private Integer currentSemester;
    private Integer weeklyHours;
    private Double currentGpa;
    private Integer currentSubjects;

    @Enumerated(EnumType.STRING)
    private AcademicGoal academicGoal;

    private boolean currentlyWorking;
    private boolean profileComplete;
    private String profilePhotoUrl;
    private Integer passwordVersion;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
