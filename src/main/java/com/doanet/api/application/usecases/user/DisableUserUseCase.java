package com.doanet.api.application.usecases.user;

import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;

import java.util.Map;

public class DisableUserUseCase {
  private final UserRepository userRepository;
  private final RecordAuditUseCase recordAuditUseCase;

  public DisableUserUseCase(UserRepository userRepository, RecordAuditUseCase recordAuditUseCase) {
    this.userRepository = userRepository;
    this.recordAuditUseCase = recordAuditUseCase;
  }

  public void execute(Long id){
    this.userRepository.disableUser(id);

    this.recordAuditUseCase.execute(
      AuditAction.DEACTIVATE,
      AuditedEntity.USER,
      id,
      Map.of("active", true),
      Map.of("active", false)
    );
  }
}
