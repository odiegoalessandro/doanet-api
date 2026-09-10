package com.doanet.api.infra.gateways;

import com.doanet.api.application.gateways.AuditStateSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JacksonAuditStateSerializer implements AuditStateSerializer {
  private final ObjectMapper objectMapper;

  public JacksonAuditStateSerializer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public String serialize(Map<String, Object> state) {
    if (state == null) {
      return null;
    }

    try {
      return this.objectMapper.writeValueAsString(state);
    } catch (JsonProcessingException exception) {
      throw new IllegalStateException("Não foi possivel serializar o estado de auditoria", exception);
    }
  }
}
