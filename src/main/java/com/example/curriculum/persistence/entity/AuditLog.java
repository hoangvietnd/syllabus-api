package com.example.curriculum.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "audit_logs", indexes = {
        @Index(name = "idx_audit_entity", columnList = "entity_type, entity_id"),
        @Index(name = "idx_audit_actor", columnList = "actor_id")
})
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ai thực hiện hành động
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private User actor;

    @Column(nullable = false, length = 100)
    private String action; // ví dụ: CREATE_COURSE, UPDATE_LESSON

    @Column(name = "entity_type", nullable = false, length = 100)
    private String entityType; // COURSE, MODULE, LESSON, MATERIAL

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    // payload chi tiết (JSON text, column jsonb trong migration)
    @Column(columnDefinition = "jsonb")
    private String payload;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

}
