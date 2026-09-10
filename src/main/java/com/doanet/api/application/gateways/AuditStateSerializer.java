package com.doanet.api.application.gateways;

import java.util.Map;

public interface AuditStateSerializer {
  String serialize(Map<String, Object> state);
}
