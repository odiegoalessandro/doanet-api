package com.doanet.api.config;

import com.doanet.api.application.gateways.AuditLogRepository;
import com.doanet.api.application.gateways.AuditStateSerializer;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.infra.gateways.AuditLogEntityMapper;
import com.doanet.api.infra.gateways.AuditLogRepositoryImpl;
import com.doanet.api.infra.persistence.JpaAuditLogRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditConfig {
  @Bean
  public RecordAuditUseCase recordAuditUseCase(
      AuditLogRepository auditLogRepository, AuditStateSerializer auditStateSerializer) {
    return new RecordAuditUseCase(auditLogRepository, auditStateSerializer);
  }

  @Bean
  public AuditLogRepository auditLogRepository(
      JpaAuditLogRepository jpaAuditLogRepository, AuditLogEntityMapper auditLogEntityMapper) {
    return new AuditLogRepositoryImpl(jpaAuditLogRepository, auditLogEntityMapper);
  }
}
