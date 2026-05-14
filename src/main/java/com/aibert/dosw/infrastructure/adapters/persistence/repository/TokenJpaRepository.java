package com.aibert.dosw.infrastructure.adapters.persistence.repository;

import com.aibert.dosw.infrastructure.adapters.persistence.entity.EmailVerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TokenJpaRepository extends JpaRepository<EmailVerificationTokenEntity, Long> {
    Optional<EmailVerificationTokenEntity> findByToken(String token);

    @Query("SELECT t FROM EmailVerificationTokenEntity t WHERE t.userId = :userId ORDER BY t.id DESC LIMIT 1")
    Optional<EmailVerificationTokenEntity> findLatestByUserId(@Param("userId") UUID userId);
}
