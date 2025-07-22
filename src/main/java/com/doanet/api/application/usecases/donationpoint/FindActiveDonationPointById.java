package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;

public class FindActiveDonationPointById {
  private final DonationPointRepository donationPointRepository;

  public FindActiveDonationPointById(DonationPointRepository donationPointRepository) {
    this.donationPointRepository = donationPointRepository;
  }

  public DonationPoint execute(Long id){
    return this.donationPointRepository.findByIdActive(id)
      .orElseThrow(() -> new ResourceNotFoundException("Não foi possivel achar um ponto de doação ativo com este ID"));
  }
}
