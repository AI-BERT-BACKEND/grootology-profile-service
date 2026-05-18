package com.aibert.dosw.infrastructure.adapters.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID adminId;

    @Column(nullable = false)
    private UUID targetUserId;

    @Column(nullable = false)
    private String changedFields;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String previousValues;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
