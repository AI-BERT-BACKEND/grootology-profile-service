package com.aibert.dosw.domain.ports.out;

import com.aibert.dosw.domain.model.user.EmailVerificationToken;
import java.util.Optional;

public interface TokenRepositoryPort {
    EmailVerificationToken save(EmailVerificationToken token);
    Optional<EmailVerificationToken> findByToken(String token);
}
