package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.gateways.OngRepository;

public class DeleteDonationPointUseCase {
  private final OngRepository ongRepository;

  public DeleteDonationPointUseCase(OngRepository ongRepository) {
    this.ongRepository = ongRepository;
  }

  public void execute(Long id){
    this.ongRepository.deleteById(id);
  }
}
