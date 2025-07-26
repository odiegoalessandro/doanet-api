package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.gateways.DonationPointRepository;

public class DeleteDonationPointUseCase {
  private final DonationPointRepository donationPointRepository;

  public DeleteDonationPointUseCase(DonationPointRepository donationPointRepository) {
    this.donationPointRepository = donationPointRepository;
  }

  public void execute(Long id){
    this.donationPointRepository.deleteById(id);
  }
}
