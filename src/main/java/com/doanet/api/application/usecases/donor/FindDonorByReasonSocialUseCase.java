package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.domain.entities.donor.Donor;

public class FindDonorByReasonSocialUseCase {
  private DonorRepository donorRepository;

  public FindDonorByReasonSocialUseCase(DonorRepository donorRepository) {
    this.donorRepository = donorRepository;
  }

  public Donor execute(String reasonSocial){
    return this.donorRepository.findByReasonSocialIgnoreCase(reasonSocial)
      .orElseThrow(() -> new ResourceNotFoundException("Não foi possivel achar nenhum doador com esta razão social"));
  }
}
