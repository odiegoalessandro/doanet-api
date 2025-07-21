package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.gateways.DonorRepository;

public class DeleteDonorUseCase {
  private final FindActiveDonorByIdUseCase findActiveDonorByIdUseCase;
  private final DonorRepository donorRepository;

  public DeleteDonorUseCase(FindActiveDonorByIdUseCase findActiveDonorByIdUseCase, DonorRepository donorRepository) {
    this.findActiveDonorByIdUseCase = findActiveDonorByIdUseCase;
    this.donorRepository = donorRepository;
  }

  public void execute(Long id){
    this.donorRepository.deleteById(id);
  }
}
