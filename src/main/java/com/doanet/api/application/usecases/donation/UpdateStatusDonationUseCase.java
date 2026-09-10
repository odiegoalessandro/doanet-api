package com.doanet.api.application.usecases.donation;

import com.doanet.api.application.gateways.DonationRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.domain.entities.donation.Donation;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import com.doanet.api.domain.enums.DonationStatus;
import com.doanet.api.domain.validator.DonationStatusValidator;
import java.util.Map;

public class UpdateStatusDonationUseCase {
  private final DonationRepository donationRepository;
  private final FindDonationByIdUseCase findDonationByIdUseCase;
  private final RecordAuditUseCase recordAuditUseCase;

  public UpdateStatusDonationUseCase(
      DonationRepository donationRepository,
      FindDonationByIdUseCase findDonationByIdUseCase,
      RecordAuditUseCase recordAuditUseCase) {
    this.donationRepository = donationRepository;
    this.findDonationByIdUseCase = findDonationByIdUseCase;
    this.recordAuditUseCase = recordAuditUseCase;
  }

  public Donation execute(Long id, DonationStatus status) {
    Donation donation = this.findDonationByIdUseCase.execute(id);

    DonationStatusValidator.validate(donation.getStatus());

    var previousStatus = donation.getStatus();
    donation.setStatus(status);

    var savedDonation = donationRepository.save(donation);

    this.recordAuditUseCase.execute(
        AuditAction.STATUS_CHANGE,
        AuditedEntity.DONATION,
        savedDonation.getId(),
        Map.of("status", previousStatus.name()),
        Map.of("status", savedDonation.getStatus().name()));

    return savedDonation;
  }
}
