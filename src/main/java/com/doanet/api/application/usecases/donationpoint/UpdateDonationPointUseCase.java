package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.commands.UpdateDonationPointCommand;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;

public class UpdateDonationPointUseCase {
  private final DonationPointRepository donationPointRepository;
  private final FindActiveDonationPointByIdUseCase findActiveDonationPointByIdUseCase;

  public UpdateDonationPointUseCase(DonationPointRepository donationPointRepository,
                                    FindActiveDonationPointByIdUseCase findActiveDonationPointByIdUseCase) {
    this.donationPointRepository = donationPointRepository;
    this.findActiveDonationPointByIdUseCase = findActiveDonationPointByIdUseCase;
  }

  public DonationPoint execute(Long id, UpdateDonationPointCommand donationPointCommand){
    var donationPoint = this.findActiveDonationPointByIdUseCase.execute(id);

    donationPoint.updateUserData(
      donationPointCommand.name(),
      donationPointCommand.email(),
      donationPointCommand.phone(),
      donationPointCommand.description()
    );

    return this.donationPointRepository.save(donationPoint);
  }
}
