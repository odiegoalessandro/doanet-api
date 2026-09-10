package com.doanet.api.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuditLogRepository extends JpaRepository<AuditLogEntity, Long> {
}
