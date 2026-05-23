package com.aibert.dosw.infrastructure.adapters.persistence.mapper;

import com.aibert.dosw.domain.model.user.AcademicGoal;
import com.aibert.dosw.domain.model.user.Availability;
import com.aibert.dosw.domain.model.user.Career;
import com.aibert.dosw.domain.model.user.EmailVerificationToken;
import com.aibert.dosw.domain.model.user.Role;
import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.domain.model.user.UserStatus;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.EmailVerificationTokenEntity;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersistenceMapperTest {

    private final UserPersistenceMapper userMapper = Mappers.getMapper(UserPersistenceMapper.class);
    private final TokenPersistenceMapper tokenMapper = Mappers.getMapper(TokenPersistenceMapper.class);

    @Test
    void userMapper_toEntity_y_toDomain_mapeaTodosLosCampos() {
        LocalDateTime now = LocalDateTime.now();
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .id(userId)
                .fullName("Usuario Mapper")
                .email("mapper@mail.escuelaing.edu.co")
                .password("hash")
                .verified(true)
                .role(Role.ADMIN)
                .status(UserStatus.INACTIVO)
                .career(Career.INGENIERIA_SISTEMAS)
                .doubleDegreeCareer(Career.INGENIERIA_INDUSTRIAL)
                .currentSemester(7)
                .weeklyHours(20)
                .dailyStudyHours(4)
                .currentGpa(4.2)
                .currentSubjects(List.of("ARQ", "SOFT"))
                .academicGoal(AcademicGoal.APROBAR_TODO)
                .currentlyWorking(true)
                .availability(Availability.NOCHE)
                .profileComplete(true)
                .profilePhotoUrl("uploads/profiles/photo.png")
                .userName("mapper.user")
                .passwordVersion(3)
                .failedAttempts(1)
                .lockedUntil(now.plusMinutes(30))
                .createdAt(now)
                .build();

        UserEntity entity = userMapper.toEntity(user);
        assertNotNull(entity);
        assertEquals(userId, entity.getId());
        assertEquals(user.getEmail(), entity.getEmail());
        assertEquals(user.getRole(), entity.getRole());
        assertEquals(user.getCurrentSubjects(), entity.getCurrentSubjects());
        assertEquals(user.getLockedUntil(), entity.getLockedUntil());

        User roundTrip = userMapper.toDomain(entity);
        assertNotNull(roundTrip);
        assertEquals(user.getId(), roundTrip.getId());
        assertEquals(user.getFullName(), roundTrip.getFullName());
        assertEquals(user.getCareer(), roundTrip.getCareer());
        assertEquals(user.getDoubleDegreeCareer(), roundTrip.getDoubleDegreeCareer());
        assertEquals(user.getCurrentSemester(), roundTrip.getCurrentSemester());
        assertEquals(user.getWeeklyHours(), roundTrip.getWeeklyHours());
        assertEquals(user.getDailyStudyHours(), roundTrip.getDailyStudyHours());
        assertEquals(user.getCurrentGpa(), roundTrip.getCurrentGpa());
        assertEquals(user.getCurrentSubjects(), roundTrip.getCurrentSubjects());
        assertEquals(user.getAcademicGoal(), roundTrip.getAcademicGoal());
        assertEquals(user.isCurrentlyWorking(), roundTrip.isCurrentlyWorking());
        assertEquals(user.getAvailability(), roundTrip.getAvailability());
        assertEquals(user.isProfileComplete(), roundTrip.isProfileComplete());
        assertEquals(user.getProfilePhotoUrl(), roundTrip.getProfilePhotoUrl());
        assertEquals(user.getUserName(), roundTrip.getUserName());
        assertEquals(user.getPasswordVersion(), roundTrip.getPasswordVersion());
        assertEquals(user.getFailedAttempts(), roundTrip.getFailedAttempts());
        assertEquals(user.getLockedUntil(), roundTrip.getLockedUntil());
        assertEquals(user.getCreatedAt(), roundTrip.getCreatedAt());
    }

    @Test
    void tokenMapper_toEntity_y_toDomain_mapeaTodosLosCampos() {
        LocalDateTime now = LocalDateTime.now();
        EmailVerificationToken token = EmailVerificationToken.builder()
                .id(10L)
                .token("123456")
                .userId(UUID.randomUUID())
                .expiresAt(now.plusMinutes(5))
                .used(false)
                .failedAttempts(2)
                .blockedUntil(now.plusMinutes(15))
                .build();

        EmailVerificationTokenEntity entity = tokenMapper.toEntity(token);
        assertNotNull(entity);
        assertEquals(token.getId(), entity.getId());
        assertEquals(token.getToken(), entity.getToken());
        assertEquals(token.getUserId(), entity.getUserId());
        assertEquals(token.getExpiresAt(), entity.getExpiresAt());
        assertEquals(token.isUsed(), entity.isUsed());
        assertEquals(token.getFailedAttempts(), entity.getFailedAttempts());
        assertEquals(token.getBlockedUntil(), entity.getBlockedUntil());

        EmailVerificationToken roundTrip = tokenMapper.toDomain(entity);
        assertNotNull(roundTrip);
        assertEquals(token.getId(), roundTrip.getId());
        assertEquals(token.getToken(), roundTrip.getToken());
        assertEquals(token.getUserId(), roundTrip.getUserId());
        assertEquals(token.getExpiresAt(), roundTrip.getExpiresAt());
        assertEquals(token.isUsed(), roundTrip.isUsed());
        assertEquals(token.getFailedAttempts(), roundTrip.getFailedAttempts());
        assertEquals(token.getBlockedUntil(), roundTrip.getBlockedUntil());
    }
}
