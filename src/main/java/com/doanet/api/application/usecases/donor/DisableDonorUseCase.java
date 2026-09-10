package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import java.util.LinkedHashMap;
import java.util.Map;

public class DisableDonorUseCase {
  private final FindDonorByIdUseCase findDonorByIdUseCase;
  private final UserRepository userRepository;
  private final RecordAuditUseCase recordAuditUseCase;

  public DisableDonorUseCase(
      FindDonorByIdUseCase findDonorByIdUseCase,
      UserRepository userRepository,
      RecordAuditUseCase recordAuditUseCase) {
    this.findDonorByIdUseCase = findDonorByIdUseCase;
    this.userRepository = userRepository;
    this.recordAuditUseCase = recordAuditUseCase;
  }

  public void execute(Long id) {
    var donor = this.findDonorByIdUseCase.execute(id, true);

    this.userRepository.disableUser(donor.getUser().getId());

    Map<String, Object> beforeState = new LinkedHashMap<>();
    beforeState.put("active", true);
    beforeState.put("userId", donor.getUser().getId());

    Map<String, Object> afterState = new LinkedHashMap<>();
    afterState.put("active", false);
    afterState.put("userId", donor.getUser().getId());

    this.recordAuditUseCase.execute(
        AuditAction.DEACTIVATE, AuditedEntity.DONOR, donor.getId(), beforeState, afterState);
  }
}
