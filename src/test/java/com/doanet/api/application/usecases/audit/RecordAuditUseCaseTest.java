package com.doanet.api.application.usecases.audit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.doanet.api.application.gateways.AuditLogRepository;
import com.doanet.api.application.gateways.AuditStateSerializer;
import com.doanet.api.domain.entities.audit.AuditLog;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class RecordAuditUseCaseTest {

  private AuditLogRepository auditLogRepository;
  private AuditStateSerializer auditStateSerializer;
  private RecordAuditUseCase recordAuditUseCase;

  @BeforeEach
  void setUp() {
    auditLogRepository = mock(AuditLogRepository.class);
    auditStateSerializer = mock(AuditStateSerializer.class);
    recordAuditUseCase = new RecordAuditUseCase(auditLogRepository, auditStateSerializer);
  }

  @Test
  void shouldSaveAuditLogWithSerializedStates() {
    Map<String, Object> before = Map.of("status", "PENDING");
    Map<String, Object> after = Map.of("status", "APPROVED");

    when(auditStateSerializer.serialize(before)).thenReturn("{\"status\":\"PENDING\"}");
    when(auditStateSerializer.serialize(after)).thenReturn("{\"status\":\"APPROVED\"}");

    recordAuditUseCase.execute(
        AuditAction.STATUS_CHANGE, AuditedEntity.DONATION, 10L, before, after);

    var captor = ArgumentCaptor.forClass(AuditLog.class);
    verify(auditLogRepository).save(captor.capture());

    var saved = captor.getValue();
    assertEquals(AuditAction.STATUS_CHANGE, saved.getAction());
    assertEquals(AuditedEntity.DONATION, saved.getEntityType());
    assertEquals(10L, saved.getEntityId());
    assertEquals("{\"status\":\"PENDING\"}", saved.getBeforeState());
    assertEquals("{\"status\":\"APPROVED\"}", saved.getAfterState());
    assertNotNull(saved.getOccurredAt());
  }

  @Test
  void shouldLeaveAuthorNullUntilAuthenticationExists() {
    recordAuditUseCase.execute(
        AuditAction.CREATE, AuditedEntity.ITEM, 1L, null, Map.of("name", "Arroz"));

    var captor = ArgumentCaptor.forClass(AuditLog.class);
    verify(auditLogRepository).save(captor.capture());

    assertNull(captor.getValue().getAuthorId());
  }

  @Test
  void shouldSerializeNullStatesAsNull() {
    when(auditStateSerializer.serialize(null)).thenReturn(null);

    recordAuditUseCase.execute(AuditAction.DEACTIVATE, AuditedEntity.USER, 2L, null, null);

    var captor = ArgumentCaptor.forClass(AuditLog.class);
    verify(auditLogRepository).save(captor.capture());

    assertNull(captor.getValue().getBeforeState());
    assertNull(captor.getValue().getAfterState());
  }
}
