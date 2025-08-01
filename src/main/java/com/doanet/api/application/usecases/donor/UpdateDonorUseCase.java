package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.commands.UpdateDonorCommand;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.domain.entities.donor.Donor;

public class UpdateDonorUseCase {
  private final DonorRepository donorRepository;
  private final FindDonorByIdUseCase findDonorByIdUseCase;

  public UpdateDonorUseCase(DonorRepository donorRepository, FindDonorByIdUseCase findDonorByIdUseCase) {
    this.donorRepository = donorRepository;
    this.findDonorByIdUseCase = findDonorByIdUseCase;
  }

  public Donor execute(Long id, UpdateDonorCommand donorCommand, boolean isActive) {
    Donor donor = this.findDonorByIdUseCase.execute(id, isActive);

    donor.updateUserData(donorCommand.name(), donorCommand.email(), donorCommand.phone());

    return this.donorRepository.save(donor);
  }
}
