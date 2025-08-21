package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.gateways.UserRepository;

public class DisableDonorUseCase {
  private final FindDonorByIdUseCase findDonorByIdUseCase;
  private final UserRepository userRepository;

  public DisableDonorUseCase(FindDonorByIdUseCase findDonorByIdUseCase,
                             UserRepository userRepository) {
    this.findDonorByIdUseCase = findDonorByIdUseCase;
    this.userRepository = userRepository;
  }

  public void execute(Long id){
    var donor = this.findDonorByIdUseCase.execute(id, true);

    this.userRepository.disableUser(donor.getUser().getId());
  }
}
