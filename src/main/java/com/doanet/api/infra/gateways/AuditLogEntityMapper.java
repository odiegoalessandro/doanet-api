package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.audit.AuditLog;
import com.doanet.api.infra.persistence.AuditLogEntity;
import org.springframework.stereotype.Component;

@Component
public class AuditLogEntityMapper {
  public AuditLogEntity toEntity(AuditLog auditLog) {
    return new AuditLogEntity(
        auditLog.getId(),
        auditLog.getAuthorId(),
        auditLog.getAction(),
        auditLog.getEntityType(),
        auditLog.getEntityId(),
        auditLog.getOccurredAt(),
        auditLog.getBeforeState(),
        auditLog.getAfterState());
  }

  public AuditLog toDomain(AuditLogEntity auditLogEntity) {
    return new AuditLog(
        auditLogEntity.getId(),
        auditLogEntity.getAuthorId(),
        auditLogEntity.getAction(),
        auditLogEntity.getEntityType(),
        auditLogEntity.getEntityId(),
        auditLogEntity.getOccurredAt(),
        auditLogEntity.getBeforeState(),
        auditLogEntity.getAfterState());
  }
}
