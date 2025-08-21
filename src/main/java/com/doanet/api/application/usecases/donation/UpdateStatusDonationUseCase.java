package com.doanet.api.application.usecases.donation;

import com.doanet.api.application.gateways.DonationRepository;
import com.doanet.api.domain.entities.donation.Donation;
import com.doanet.api.domain.enums.DonationStatus;
import com.doanet.api.domain.validator.DonationStatusValidator;

public class UpdateStatusDonationUseCase {
  private final DonationRepository donationRepository;
  private final FindDonationByIdUseCase findDonationByIdUseCase;

  public UpdateStatusDonationUseCase(DonationRepository donationRepository,
                                     FindDonationByIdUseCase findDonationByIdUseCase) {
    this.donationRepository = donationRepository;
    this.findDonationByIdUseCase = findDonationByIdUseCase;
  }

  public Donation execute(Long id, DonationStatus status) {
    Donation donation = this.findDonationByIdUseCase.execute(id);

    DonationStatusValidator.validate(donation.getStatus());

    donation.setStatus(status);

    return donationRepository.save(donation);
  }
}
