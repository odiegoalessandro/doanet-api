package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.commands.UpdateDonorCommand;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.domain.entities.donor.Donor;

public class UpdateDonorUseCase {
  private final DonorRepository donorRepository;
  private final FindActiveDonorByIdUseCase findActiveDonorByIdUseCase;

  public UpdateDonorUseCase(DonorRepository donorRepository, FindActiveDonorByIdUseCase findActiveDonorByIdUseCase) {
    this.donorRepository = donorRepository;
    this.findActiveDonorByIdUseCase = findActiveDonorByIdUseCase;
  }

  public Donor execute(Long id, UpdateDonorCommand donorCommand){
    Donor donor = this.findActiveDonorByIdUseCase.execute(id);

    donor.updateUserData(donorCommand.name(), donorCommand.email(), donorCommand.phone());

    return this.donorRepository.save(donor);
  }
}
