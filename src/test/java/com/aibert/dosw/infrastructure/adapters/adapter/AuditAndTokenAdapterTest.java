package com.aibert.dosw.infrastructure.adapters.adapter;

import com.aibert.dosw.domain.model.user.EmailVerificationToken;
import com.aibert.dosw.domain.ports.out.AuditLogPort;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.AuditLogEntity;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.EmailVerificationTokenEntity;
import com.aibert.dosw.infrastructure.adapters.persistence.mapper.TokenPersistenceMapper;
import com.aibert.dosw.infrastructure.adapters.persistence.repository.AuditLogJpaRepository;
import com.aibert.dosw.infrastructure.adapters.persistence.repository.TokenJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditAndTokenAdapterTest {

    // --- AuditLogAdapter ---
    @Mock private AuditLogJpaRepository auditLogJpaRepository;
    @InjectMocks private AuditLogAdapter auditLogAdapter;

    @Test
    void auditLogAdapter_save_llamaJpaRepository() {
        UUID adminId = UUID.randomUUID();
        UUID targetId = UUID.randomUUID();
        when(auditLogJpaRepository.save(any())).thenReturn(AuditLogEntity.builder()
                .adminId(adminId).targetUserId(targetId)
                .changedFields("email").previousValues("{}")
                .timestamp(LocalDateTime.now()).build());

        auditLogAdapter.save(adminId, targetId, "email", "{}", LocalDateTime.now());

        verify(auditLogJpaRepository).save(any(AuditLogEntity.class));
    }

    // --- TokenRepositoryAdapter ---
    @Mock private TokenJpaRepository tokenJpaRepository;
    @Mock private TokenPersistenceMapper tokenMapper;
    @InjectMocks private TokenRepositoryAdapter tokenRepositoryAdapter;

    private EmailVerificationToken buildToken(UUID userId) {
        return EmailVerificationToken.builder()
                .id(1L).token("123456").userId(userId)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .used(false).failedAttempts(0).build();
    }

    private EmailVerificationTokenEntity buildTokenEntity(UUID userId) {
        return EmailVerificationTokenEntity.builder()
                .id(1L).token("123456").userId(userId)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .used(false).failedAttempts(0).build();
    }

    @Test
    void tokenAdapter_save_retornaToken() {
        UUID userId = UUID.randomUUID();
        EmailVerificationToken token = buildToken(userId);
        EmailVerificationTokenEntity entity = buildTokenEntity(userId);

        when(tokenMapper.toEntity(token)).thenReturn(entity);
        when(tokenJpaRepository.save(entity)).thenReturn(entity);
        when(tokenMapper.toDomain(entity)).thenReturn(token);

        EmailVerificationToken result = tokenRepositoryAdapter.save(token);
        assertNotNull(result);
        assertEquals("123456", result.getToken());
    }

    @Test
    void tokenAdapter_findByToken_existe_retornaToken() {
        UUID userId = UUID.randomUUID();
        EmailVerificationTokenEntity entity = buildTokenEntity(userId);
        EmailVerificationToken token = buildToken(userId);

        when(tokenJpaRepository.findByToken("123456")).thenReturn(Optional.of(entity));
        when(tokenMapper.toDomain(entity)).thenReturn(token);

        Optional<EmailVerificationToken> result = tokenRepositoryAdapter.findByToken("123456");
        assertTrue(result.isPresent());
    }

    @Test
    void tokenAdapter_findByToken_noExiste_retornaVacio() {
        when(tokenJpaRepository.findByToken("bad")).thenReturn(Optional.empty());
        assertTrue(tokenRepositoryAdapter.findByToken("bad").isEmpty());
    }

    @Test
    void tokenAdapter_findLatestByUserId_existe_retornaToken() {
        UUID userId = UUID.randomUUID();
        EmailVerificationTokenEntity entity = buildTokenEntity(userId);
        EmailVerificationToken token = buildToken(userId);

        when(tokenJpaRepository.findLatestByUserId(userId)).thenReturn(Optional.of(entity));
        when(tokenMapper.toDomain(entity)).thenReturn(token);

        Optional<EmailVerificationToken> result = tokenRepositoryAdapter.findLatestByUserId(userId);
        assertTrue(result.isPresent());
    }

    @Test
    void tokenAdapter_findLatestByUserId_noExiste_retornaVacio() {
        UUID userId = UUID.randomUUID();
        when(tokenJpaRepository.findLatestByUserId(userId)).thenReturn(Optional.empty());
        assertTrue(tokenRepositoryAdapter.findLatestByUserId(userId).isEmpty());
    }

    @Test
    void tokenAdapter_invalidateAllByUserId_llamaJpa() {
        UUID userId = UUID.randomUUID();
        doNothing().when(tokenJpaRepository).invalidateAllByUserId(userId);
        tokenRepositoryAdapter.invalidateAllByUserId(userId);
        verify(tokenJpaRepository).invalidateAllByUserId(userId);
    }
}
