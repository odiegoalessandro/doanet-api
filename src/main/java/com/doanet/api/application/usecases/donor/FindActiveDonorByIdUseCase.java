package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.domain.entities.donor.Donor;

public class FindActiveDonorByIdUseCase {
  private final DonorRepository donorRepository;

  public FindActiveDonorByIdUseCase(DonorRepository donorRepository) {
    this.donorRepository = donorRepository;
  }

  public Donor execute(Long id){
    return this.donorRepository.findByIdActive(id)
      .orElseThrow(() -> new ResourceNotFoundException("Não foi possivel achar nenhum doador ativo com este ID"));
  }
}
