package com.aibert.dosw.domain.ports.out;

import com.aibert.dosw.domain.model.user.EmailVerificationToken;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepositoryPort {
    EmailVerificationToken save(EmailVerificationToken token);
    Optional<EmailVerificationToken> findByToken(String token);
    Optional<EmailVerificationToken> findLatestByUserId(UUID userId);
}
