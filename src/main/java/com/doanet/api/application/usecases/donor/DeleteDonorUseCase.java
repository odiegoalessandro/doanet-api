package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.gateways.DonorRepository;

public class DeleteDonorUseCase {
  private final FindDonorByIdUseCase findDonorByIdUseCase;
  private final DonorRepository donorRepository;

  public DeleteDonorUseCase(FindDonorByIdUseCase findDonorByIdUseCase, DonorRepository donorRepository) {
    this.findDonorByIdUseCase = findDonorByIdUseCase;
    this.donorRepository = donorRepository;
  }

  public void execute(Long id){
    this.donorRepository.deleteById(id);
  }
}
