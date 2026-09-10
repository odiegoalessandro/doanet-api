package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import java.util.LinkedHashMap;
import java.util.Map;

public class DisableOngByIdUseCase {
  private final UserRepository userRepository;
  private final FindOngByIdUseCase findOngByIdUseCase;
  private final RecordAuditUseCase recordAuditUseCase;

  public DisableOngByIdUseCase(
      UserRepository userRepository,
      FindOngByIdUseCase findOngByIdUseCase,
      RecordAuditUseCase recordAuditUseCase) {
    this.userRepository = userRepository;
    this.findOngByIdUseCase = findOngByIdUseCase;
    this.recordAuditUseCase = recordAuditUseCase;
  }

  public void execute(Long id) {
    var ong = this.findOngByIdUseCase.execute(id, true);

    this.userRepository.disableUser(ong.getUser().getId());

    Map<String, Object> beforeState = new LinkedHashMap<>();
    beforeState.put("active", true);
    beforeState.put("userId", ong.getUser().getId());

    Map<String, Object> afterState = new LinkedHashMap<>();
    afterState.put("active", false);
    afterState.put("userId", ong.getUser().getId());

    this.recordAuditUseCase.execute(
        AuditAction.DEACTIVATE, AuditedEntity.ONG, ong.getId(), beforeState, afterState);
  }
}
