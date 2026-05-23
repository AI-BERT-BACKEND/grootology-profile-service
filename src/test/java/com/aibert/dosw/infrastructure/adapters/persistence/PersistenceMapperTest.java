package com.aibert.dosw.infrastructure.adapters.persistence;

import com.aibert.dosw.domain.model.user.*;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.EmailVerificationTokenEntity;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.PasswordResetTokenEntity;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.UserEntity;
import com.aibert.dosw.infrastructure.adapters.persistence.mapper.PasswordResetTokenMapper;
import com.aibert.dosw.infrastructure.adapters.persistence.mapper.TokenPersistenceMapper;
import com.aibert.dosw.infrastructure.adapters.persistence.mapper.UserPersistenceMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceMapperTest {

    private final UserPersistenceMapper userMapper = Mappers.getMapper(UserPersistenceMapper.class);
    private final TokenPersistenceMapper tokenMapper = Mappers.getMapper(TokenPersistenceMapper.class);
    private final PasswordResetTokenMapper passwordResetMapper = Mappers.getMapper(PasswordResetTokenMapper.class);

    @Test
    void userMapper_toDomain() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        UserEntity entity = UserEntity.builder()
                .id(id).fullName("Test User").email("test@mail.escuelaing.edu.co")
                .password("hashed").verified(true).role(Role.ESTUDIANTE).status(UserStatus.ACTIVO)
                .career(Career.INGENIERIA_SISTEMAS).currentSemester(5).weeklyHours(15)
                .dailyStudyHours(3).currentGpa(3.8).currentSubjects(List.of("Algorithms"))
                .academicGoal(AcademicGoal.EXCELENCIA).currentlyWorking(false)
                .availability(Availability.NOCHE).profileComplete(true)
                .profilePhotoUrl("http://url/photo.jpg").userName("test.user")
                .passwordVersion(1).createdAt(now).build();

        User user = userMapper.toDomain(entity);

        assertEquals(id, user.getId());
        assertEquals("Test User", user.getFullName());
        assertEquals("test@mail.escuelaing.edu.co", user.getEmail());
        assertEquals(Role.ESTUDIANTE, user.getRole());
        assertEquals(Career.INGENIERIA_SISTEMAS, user.getCareer());
        assertEquals(5, user.getCurrentSemester());
        assertTrue(user.isVerified());
    }

    @Test
    void userMapper_toEntity() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id).fullName("Test User").email("test@mail.escuelaing.edu.co")
                .password("hashed").verified(true).role(Role.ESTUDIANTE).status(UserStatus.ACTIVO)
                .career(Career.INGENIERIA_SISTEMAS).currentSemester(5).weeklyHours(15)
                .dailyStudyHours(3).currentGpa(3.8).currentSubjects(List.of("Algorithms"))
                .academicGoal(AcademicGoal.EXCELENCIA).currentlyWorking(false)
                .availability(Availability.NOCHE).profileComplete(true)
                .profilePhotoUrl("http://url/photo.jpg").userName("test.user")
                .passwordVersion(1).createdAt(LocalDateTime.now()).build();

        UserEntity entity = userMapper.toEntity(user);

        assertEquals(id, entity.getId());
        assertEquals("Test User", entity.getFullName());
        assertEquals(Role.ESTUDIANTE, entity.getRole());
        assertEquals(Career.INGENIERIA_SISTEMAS, entity.getCareer());
    }

    @Test
    void tokenMapper_toDomain() {
        UUID userId = UUID.randomUUID();
        LocalDateTime expires = LocalDateTime.now().plusMinutes(5);
        EmailVerificationTokenEntity entity = EmailVerificationTokenEntity.builder()
                .id(1L).token("123456").userId(userId).expiresAt(expires)
                .used(false).failedAttempts(0).blockedUntil(null).build();

        EmailVerificationToken token = tokenMapper.toDomain(entity);

        assertEquals("123456", token.getToken());
        assertEquals(userId, token.getUserId());
        assertEquals(expires, token.getExpiresAt());
        assertFalse(token.isUsed());
    }

    @Test
    void tokenMapper_toEntity() {
        UUID userId = UUID.randomUUID();
        LocalDateTime expires = LocalDateTime.now().plusMinutes(5);
        EmailVerificationToken token = EmailVerificationToken.builder()
                .id(1L).token("123456").userId(userId).expiresAt(expires)
                .used(false).failedAttempts(0).blockedUntil(null).build();

        EmailVerificationTokenEntity entity = tokenMapper.toEntity(token);

        assertEquals("123456", entity.getToken());
        assertEquals(userId, entity.getUserId());
        assertFalse(entity.isUsed());
    }

    @Test
    void passwordResetMapper_toDomain() {
        UUID userId = UUID.randomUUID();
        LocalDateTime expires = LocalDateTime.now().plusMinutes(2);
        PasswordResetTokenEntity entity = PasswordResetTokenEntity.builder()
                .id(1L).token("reset-token").userId(userId).expiresAt(expires).used(false).build();

        PasswordResetToken token = passwordResetMapper.toDomain(entity);

        assertEquals("reset-token", token.getToken());
        assertEquals(userId, token.getUserId());
        assertEquals(expires, token.getExpiresAt());
        assertFalse(token.isUsed());
    }

    @Test
    void passwordResetMapper_toEntity() {
        UUID userId = UUID.randomUUID();
        LocalDateTime expires = LocalDateTime.now().plusMinutes(2);
        PasswordResetToken token = PasswordResetToken.builder()
                .id(1L).token("reset-token").userId(userId).expiresAt(expires).used(false).build();

        PasswordResetTokenEntity entity = passwordResetMapper.toEntity(token);

        assertEquals("reset-token", entity.getToken());
        assertEquals(userId, entity.getUserId());
        assertFalse(entity.isUsed());
    }
}
