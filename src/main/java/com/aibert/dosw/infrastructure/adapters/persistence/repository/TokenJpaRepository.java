package com.aibert.dosw.infrastructure.adapters.persistence.repository;

import com.aibert.dosw.infrastructure.adapters.persistence.entity.EmailVerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface TokenJpaRepository extends JpaRepository<EmailVerificationTokenEntity, Long> {
    Optional<EmailVerificationTokenEntity> findByToken(String token);

    @Query("SELECT t FROM EmailVerificationTokenEntity t WHERE t.userId = :userId ORDER BY t.id DESC LIMIT 1")
    Optional<EmailVerificationTokenEntity> findLatestByUserId(@Param("userId") UUID userId);

    @Modifying
    @Transactional
    @Query("UPDATE EmailVerificationTokenEntity t SET t.used = true WHERE t.userId = :userId AND t.used = false")
    void invalidateAllByUserId(@Param("userId") UUID userId);
}
