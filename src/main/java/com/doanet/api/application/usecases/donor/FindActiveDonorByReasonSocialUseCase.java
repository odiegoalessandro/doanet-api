package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.domain.entities.donor.Donor;

public class FindActiveDonorByReasonSocialUseCase {
  private DonorRepository donorRepository;

  public FindActiveDonorByReasonSocialUseCase(DonorRepository donorRepository) {
    this.donorRepository = donorRepository;
  }

  public Donor execute(String reasonSocial){
    return this.donorRepository.findByReasonSocialIgnoreCaseActive(reasonSocial)
      .orElseThrow(() -> new ResourceNotFoundException("Não foi possivel achar nenhum doador ativo com esta razão " +
        "social"));
  }
}
