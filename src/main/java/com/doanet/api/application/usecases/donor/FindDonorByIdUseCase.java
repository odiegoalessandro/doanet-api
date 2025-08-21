package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.domain.entities.donor.Donor;

public class FindDonorByIdUseCase {
  private final DonorRepository donorRepository;

  public FindDonorByIdUseCase(DonorRepository donorRepository) {
    this.donorRepository = donorRepository;
  }

  public Donor execute(Long id, boolean isActive){
    return this.donorRepository.findById(id, isActive)
      .orElseThrow(() -> new ResourceNotFoundException("Não foi possivel achar nenhum doador ativo com este ID"));
  }
}
