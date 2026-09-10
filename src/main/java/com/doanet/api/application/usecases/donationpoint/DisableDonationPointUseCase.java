package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import java.util.LinkedHashMap;
import java.util.Map;

public class DisableDonationPointUseCase {
  private final UserRepository userRepository;
  private final FindDonationPointByIdUseCase findDonationPointByIdUseCase;
  private final RecordAuditUseCase recordAuditUseCase;

  public DisableDonationPointUseCase(
      UserRepository userRepository,
      FindDonationPointByIdUseCase findDonationPointByIdUseCase,
      RecordAuditUseCase recordAuditUseCase) {
    this.userRepository = userRepository;
    this.findDonationPointByIdUseCase = findDonationPointByIdUseCase;
    this.recordAuditUseCase = recordAuditUseCase;
  }

  public void execute(Long id) {
    var donationPoint = findDonationPointByIdUseCase.execute(id, true);

    this.userRepository.disableUser(donationPoint.getUser().getId());

    Map<String, Object> beforeState = new LinkedHashMap<>();
    beforeState.put("active", true);
    beforeState.put("userId", donationPoint.getUser().getId());

    Map<String, Object> afterState = new LinkedHashMap<>();
    afterState.put("active", false);
    afterState.put("userId", donationPoint.getUser().getId());

    this.recordAuditUseCase.execute(
        AuditAction.DEACTIVATE,
        AuditedEntity.DONATION_POINT,
        donationPoint.getId(),
        beforeState,
        afterState);
  }
}
