package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.commands.UpdateDonationPointCommand;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;

public class UpdateDonationPointUseCase {
  private final DonationPointRepository donationPointRepository;
  private final FindDonationPointByIdUseCase findDonationPointByIdUseCase;

  public UpdateDonationPointUseCase(DonationPointRepository donationPointRepository,
                                    FindDonationPointByIdUseCase findDonationPointByIdUseCase) {
    this.donationPointRepository = donationPointRepository;
    this.findDonationPointByIdUseCase = findDonationPointByIdUseCase;
  }

  public DonationPoint execute(Long id, UpdateDonationPointCommand donationPointCommand){
    var donationPoint = this.findDonationPointByIdUseCase.execute(id, true);

    donationPoint.updateUserData(
      donationPointCommand.name(),
      donationPointCommand.email(),
      donationPointCommand.phone(),
      donationPointCommand.description()
    );

    return this.donationPointRepository.save(donationPoint);
  }
}
