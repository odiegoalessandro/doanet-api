package com.doanet.api.infra.persistence;

import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit_log")
public class AuditLogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "author_id")
  private Long authorId;

  @Enumerated(EnumType.STRING)
  @Column(name = "action", nullable = false, length = 30)
  private AuditAction action;

  @Enumerated(EnumType.STRING)
  @Column(name = "entity_type", nullable = false, length = 30)
  private AuditedEntity entityType;

  @Column(name = "entity_id")
  private Long entityId;

  @Column(name = "occurred_at", nullable = false)
  private Instant occurredAt;

  @Column(name = "before_state", columnDefinition = "TEXT")
  private String beforeState;

  @Column(name = "after_state", columnDefinition = "TEXT")
  private String afterState;
}
