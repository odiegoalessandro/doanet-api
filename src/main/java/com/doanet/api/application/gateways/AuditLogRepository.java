package com.doanet.api.application.gateways;

import com.doanet.api.domain.entities.audit.AuditLog;

public interface AuditLogRepository {
  AuditLog save(AuditLog auditLog);
}
