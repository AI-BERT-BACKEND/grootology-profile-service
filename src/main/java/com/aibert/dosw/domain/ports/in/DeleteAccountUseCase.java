package com.aibert.dosw.domain.ports.in;

import java.util.UUID;

public interface DeleteAccountUseCase {
    void deleteAccount(UUID userId, String currentPassword);
}
