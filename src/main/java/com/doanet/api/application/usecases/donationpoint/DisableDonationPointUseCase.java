package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.gateways.UserRepository;

public class DisableDonationPointUseCase {
  private final UserRepository userRepository;
  private final FindDonationPointByIdUseCase findDonationPointByIdUseCase;

  public DisableDonationPointUseCase(
   UserRepository userRepository,
   FindDonationPointByIdUseCase findDonationPointByIdUseCase
  ) {
    this.userRepository = userRepository;
    this.findDonationPointByIdUseCase = findDonationPointByIdUseCase;
  }

  public void execute(Long id){
    var donationPoint  = findDonationPointByIdUseCase.execute(id, true);

    this.userRepository.disableUser(donationPoint.getUser().getId());
  }
}
