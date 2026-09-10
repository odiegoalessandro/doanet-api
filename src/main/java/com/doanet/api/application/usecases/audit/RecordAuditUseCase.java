package com.doanet.api.application.usecases.audit;

import com.doanet.api.application.gateways.AuditLogRepository;
import com.doanet.api.application.gateways.AuditStateSerializer;
import com.doanet.api.domain.entities.audit.AuditLog;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import java.time.Instant;
import java.util.Map;

public class RecordAuditUseCase {
  private final AuditLogRepository auditLogRepository;
  private final AuditStateSerializer auditStateSerializer;

  public RecordAuditUseCase(
      AuditLogRepository auditLogRepository, AuditStateSerializer auditStateSerializer) {
    this.auditLogRepository = auditLogRepository;
    this.auditStateSerializer = auditStateSerializer;
  }

  public void execute(
      AuditAction action,
      AuditedEntity entityType,
      Long entityId,
      Map<String, Object> beforeState,
      Map<String, Object> afterState) {
    // TODO (#3): o autor passará a ser o usuário autenticado quando a autenticação existir.
    Long authorId = null;

    var auditLog =
        new AuditLog(
            null,
            authorId,
            action,
            entityType,
            entityId,
            Instant.now(),
            this.auditStateSerializer.serialize(beforeState),
            this.auditStateSerializer.serialize(afterState));

    this.auditLogRepository.save(auditLog);
  }
}
