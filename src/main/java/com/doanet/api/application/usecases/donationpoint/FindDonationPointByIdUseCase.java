package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;

public class FindDonationPointByIdUseCase {
  private final DonationPointRepository donationPointRepository;

  public FindDonationPointByIdUseCase(DonationPointRepository donationPointRepository) {
    this.donationPointRepository = donationPointRepository;
  }

  public DonationPoint execute(Long id, boolean isActive){
    return this.donationPointRepository.findById(id, isActive)
      .orElseThrow(() -> new ResourceNotFoundException("Não foi possivel achar um ponto de doação ativo com este ID"));
  }
}
