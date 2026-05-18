package com.aibert.dosw.domain.ports.out;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AuditLogPort {
    void save(UUID adminId, UUID targetUserId, String changedFields, String previousValues, LocalDateTime timestamp);
}
