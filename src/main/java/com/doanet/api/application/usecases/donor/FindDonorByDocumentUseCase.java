package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.domain.entities.donor.Donor;

public class FindDonorByDocumentUseCase {
  private final DonorRepository donorRepository;

  public FindDonorByDocumentUseCase(DonorRepository donorRepository) {
    this.donorRepository = donorRepository;
  }

  public Donor execute(String document){
    return this.donorRepository.findByDocument(document)
      .orElseThrow(() -> new ResourceNotFoundException("Não foi possivel achar nenhum doador com este documento"));
  }
}
