package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.domain.entities.donor.Donor;

public class FindActiveDonorByDocumentUseCase {
  private DonorRepository donorRepository;

  public FindActiveDonorByDocumentUseCase(DonorRepository donorRepository) {
    this.donorRepository = donorRepository;
  }

  public Donor execute(String document){
    return this.donorRepository.findByDocumentActive(document)
      .orElseThrow(() -> new ResourceNotFoundException("Não foi possivel achar nenhum doador ativo com este " +
        "documento"));
  }
}
