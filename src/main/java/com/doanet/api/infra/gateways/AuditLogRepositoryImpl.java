package com.doanet.api.infra.gateways;

import com.doanet.api.application.gateways.AuditLogRepository;
import com.doanet.api.domain.entities.audit.AuditLog;
import com.doanet.api.infra.persistence.JpaAuditLogRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditLogRepositoryImpl implements AuditLogRepository {
  private final JpaAuditLogRepository jpaAuditLogRepository;
  private final AuditLogEntityMapper mapper;

  public AuditLogRepositoryImpl(
      JpaAuditLogRepository jpaAuditLogRepository, AuditLogEntityMapper mapper) {
    this.jpaAuditLogRepository = jpaAuditLogRepository;
    this.mapper = mapper;
  }

  @Override
  public AuditLog save(AuditLog auditLog) {
    var savedAuditLog = this.jpaAuditLogRepository.save(this.mapper.toEntity(auditLog));

    log.info(
        "Auditoria: action={} entity={} entityId={} authorId={} occurredAt={} before={} after={}",
        savedAuditLog.getAction(),
        savedAuditLog.getEntityType(),
        savedAuditLog.getEntityId(),
        savedAuditLog.getAuthorId(),
        savedAuditLog.getOccurredAt(),
        savedAuditLog.getBeforeState(),
        savedAuditLog.getAfterState());

    return this.mapper.toDomain(savedAuditLog);
  }
}
