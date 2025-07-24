package com.doanet.api.application.usecases.donation;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.DonationRepository;
import com.doanet.api.domain.entities.donation.Donation;

public class FindDonationByIdUseCase {
  private final DonationRepository donationRepository;

  public FindDonationByIdUseCase(DonationRepository donationRepository) {
    this.donationRepository = donationRepository;
  }

  public Donation execute(Long donationId) {
    return this.donationRepository.findById(donationId)
        .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id: " + donationId));
  }
}
