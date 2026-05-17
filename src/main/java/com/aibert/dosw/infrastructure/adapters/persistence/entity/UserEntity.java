package com.aibert.dosw.infrastructure.adapters.persistence.entity;

import com.aibert.dosw.domain.model.user.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
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

    @Enumerated(EnumType.STRING)
    private Career doubleDegreeCareer;

    private Integer currentSemester;
    private Integer weeklyHours;
    private Integer dailyStudyHours;
    private Double currentGpa;
    @ElementCollection
    @CollectionTable(name = "user_subjects", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "subject")
    private List<String> currentSubjects;

    @Enumerated(EnumType.STRING)
    private AcademicGoal academicGoal;

    private boolean currentlyWorking;

    @Enumerated(EnumType.STRING)
    private Availability availability;

    private boolean profileComplete;
    private String profilePhotoUrl;

    @Column(unique = true)
    private String userName;

    private Integer passwordVersion;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
