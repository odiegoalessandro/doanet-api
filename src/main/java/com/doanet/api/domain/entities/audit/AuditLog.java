package com.doanet.api.domain.entities.audit;

import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;

import java.time.Instant;

public class AuditLog {
  private final Long id;
  private final Long authorId;
  private final AuditAction action;
  private final AuditedEntity entityType;
  private final Long entityId;
  private final Instant occurredAt;
  private final String beforeState;
  private final String afterState;

  public AuditLog(Long id,
                  Long authorId,
                  AuditAction action,
                  AuditedEntity entityType,
                  Long entityId,
                  Instant occurredAt,
                  String beforeState,
                  String afterState) {
    validateAction(action);
    validateEntityType(entityType);
    validateOccurredAt(occurredAt);

    this.id = id;
    this.authorId = authorId;
    this.action = action;
    this.entityType = entityType;
    this.entityId = entityId;
    this.occurredAt = occurredAt;
    this.beforeState = beforeState;
    this.afterState = afterState;
  }

  private void validateAction(AuditAction action) {
    if (action == null) {
      throw new IllegalArgumentException("A ação de auditoria não pode ser nula");
    }
  }

  private void validateEntityType(AuditedEntity entityType) {
    if (entityType == null) {
      throw new IllegalArgumentException("O tipo da entidade auditada não pode ser nulo");
    }
  }

  private void validateOccurredAt(Instant occurredAt) {
    if (occurredAt == null) {
      throw new IllegalArgumentException("O instante do evento de auditoria não pode ser nulo");
    }
  }

  public Long getId() {
    return id;
  }

  public Long getAuthorId() {
    return authorId;
  }

  public AuditAction getAction() {
    return action;
  }

  public AuditedEntity getEntityType() {
    return entityType;
  }

  public Long getEntityId() {
    return entityId;
  }

  public Instant getOccurredAt() {
    return occurredAt;
  }

  public String getBeforeState() {
    return beforeState;
  }

  public String getAfterState() {
    return afterState;
  }
}
