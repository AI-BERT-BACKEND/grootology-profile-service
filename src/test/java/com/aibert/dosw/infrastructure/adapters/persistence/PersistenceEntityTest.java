package com.aibert.dosw.infrastructure.adapters.persistence;

import com.aibert.dosw.domain.model.user.*;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.AuditLogEntity;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.EmailVerificationTokenEntity;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceEntityTest {

    @Test
    void auditLogEntity_builderAndGetters() {
        UUID adminId = UUID.randomUUID();
        UUID targetId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        AuditLogEntity entity = AuditLogEntity.builder()
                .id(1L)
                .adminId(adminId)
                .targetUserId(targetId)
                .changedFields("email,fullName")
                .previousValues("{\"email\":\"old@test.com\"}")
                .timestamp(now)
                .build();

        assertEquals(1L, entity.getId());
        assertEquals(adminId, entity.getAdminId());
        assertEquals(targetId, entity.getTargetUserId());
        assertEquals("email,fullName", entity.getChangedFields());
        assertEquals("{\"email\":\"old@test.com\"}", entity.getPreviousValues());
        assertEquals(now, entity.getTimestamp());
    }

    @Test
    void auditLogEntity_setters() {
        AuditLogEntity entity = new AuditLogEntity();
        UUID adminId = UUID.randomUUID();
        entity.setAdminId(adminId);
        entity.setChangedFields("role");
        assertEquals(adminId, entity.getAdminId());
        assertEquals("role", entity.getChangedFields());
    }

    @Test
    void emailVerificationTokenEntity_builderAndGetters() {
        UUID userId = UUID.randomUUID();
        LocalDateTime expires = LocalDateTime.now().plusMinutes(5);
        LocalDateTime blocked = LocalDateTime.now().plusMinutes(15);

        EmailVerificationTokenEntity entity = EmailVerificationTokenEntity.builder()
                .id(1L)
                .token("123456")
                .userId(userId)
                .expiresAt(expires)
                .used(false)
                .failedAttempts(2)
                .blockedUntil(blocked)
                .build();

        assertEquals(1L, entity.getId());
        assertEquals("123456", entity.getToken());
        assertEquals(userId, entity.getUserId());
        assertEquals(expires, entity.getExpiresAt());
        assertFalse(entity.isUsed());
        assertEquals(2, entity.getFailedAttempts());
        assertEquals(blocked, entity.getBlockedUntil());
    }

    @Test
    void emailVerificationTokenEntity_setters() {
        EmailVerificationTokenEntity entity = new EmailVerificationTokenEntity();
        entity.setToken("654321");
        entity.setUsed(true);
        entity.setFailedAttempts(1);
        assertEquals("654321", entity.getToken());
        assertTrue(entity.isUsed());
        assertEquals(1, entity.getFailedAttempts());
    }

    @Test
    void userEntity_builderAndGetters() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        UserEntity entity = UserEntity.builder()
                .id(id)
                .fullName("Test User")
                .email("test@mail.escuelaing.edu.co")
                .password("hashed")
                .verified(true)
                .role(Role.ESTUDIANTE)
                .status(UserStatus.ACTIVO)
                .career(Career.INGENIERIA_SISTEMAS)
                .doubleDegreeCareer(Career.INGENIERIA_INDUSTRIAL)
                .currentSemester(5)
                .weeklyHours(15)
                .dailyStudyHours(3)
                .currentGpa(3.8)
                .currentSubjects(List.of("Algorithms"))
                .academicGoal(AcademicGoal.EXCELENCIA)
                .currentlyWorking(false)
                .availability(Availability.NOCHE)
                .profileComplete(true)
                .profilePhotoUrl("http://url/photo.jpg")
                .userName("test.user")
                .passwordVersion(1)
                .createdAt(now)
                .build();

        assertEquals(id, entity.getId());
        assertEquals("Test User", entity.getFullName());
        assertEquals("test@mail.escuelaing.edu.co", entity.getEmail());
        assertEquals("hashed", entity.getPassword());
        assertTrue(entity.isVerified());
        assertEquals(Role.ESTUDIANTE, entity.getRole());
        assertEquals(UserStatus.ACTIVO, entity.getStatus());
        assertEquals(Career.INGENIERIA_SISTEMAS, entity.getCareer());
        assertEquals(Career.INGENIERIA_INDUSTRIAL, entity.getDoubleDegreeCareer());
        assertEquals(5, entity.getCurrentSemester());
        assertEquals(15, entity.getWeeklyHours());
        assertEquals(3, entity.getDailyStudyHours());
        assertEquals(3.8, entity.getCurrentGpa());
        assertEquals(List.of("Algorithms"), entity.getCurrentSubjects());
        assertEquals(AcademicGoal.EXCELENCIA, entity.getAcademicGoal());
        assertEquals(Availability.NOCHE, entity.getAvailability());
        assertTrue(entity.isProfileComplete());
        assertEquals("http://url/photo.jpg", entity.getProfilePhotoUrl());
        assertEquals("test.user", entity.getUserName());
        assertEquals(1, entity.getPasswordVersion());
        assertEquals(now, entity.getCreatedAt());
    }

    @Test
    void userEntity_setters() {
        UserEntity entity = new UserEntity();
        entity.setFullName("New Name");
        entity.setEmail("new@mail.escuelaing.edu.co");
        entity.setVerified(true);
        entity.setRole(Role.ADMIN);
        entity.setStatus(UserStatus.INACTIVO);
        entity.setPasswordVersion(2);

        assertEquals("New Name", entity.getFullName());
        assertEquals("new@mail.escuelaing.edu.co", entity.getEmail());
        assertTrue(entity.isVerified());
        assertEquals(Role.ADMIN, entity.getRole());
        assertEquals(UserStatus.INACTIVO, entity.getStatus());
        assertEquals(2, entity.getPasswordVersion());
    }
}
