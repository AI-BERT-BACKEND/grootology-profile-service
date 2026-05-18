package com.aibert.dosw.infrastructure.adapters.adapter;

import com.aibert.dosw.domain.ports.out.AuditLogPort;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.AuditLogEntity;
import com.aibert.dosw.infrastructure.adapters.persistence.repository.AuditLogJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuditLogAdapter implements AuditLogPort {

    private final AuditLogJpaRepository jpaRepository;

    @Override
    public void save(UUID adminId, UUID targetUserId, String changedFields, String previousValues, LocalDateTime timestamp) {
        jpaRepository.save(AuditLogEntity.builder()
                .adminId(adminId)
                .targetUserId(targetUserId)
                .changedFields(changedFields)
                .previousValues(previousValues)
                .timestamp(timestamp)
                .build());
    }
}
