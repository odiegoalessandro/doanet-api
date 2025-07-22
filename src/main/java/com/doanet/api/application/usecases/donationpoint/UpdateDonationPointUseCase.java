package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.commands.UpdateDonationPointCommand;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;

public class UpdateDonationPointUseCase {
  private final DonationPointRepository donationPointRepository;
  private final FindActiveDonationPointById findActiveDonationPointById;

  public UpdateDonationPointUseCase(DonationPointRepository donationPointRepository,
                                    FindActiveDonationPointById findActiveDonationPointById) {
    this.donationPointRepository = donationPointRepository;
    this.findActiveDonationPointById = findActiveDonationPointById;
  }

  public DonationPoint execute(Long id, UpdateDonationPointCommand donationPointCommand){
    var donationPoint = this.findActiveDonationPointById.execute(id);

    donationPoint.updateUserData(
      donationPointCommand.name(),
      donationPointCommand.email(),
      donationPointCommand.phone()
    );

    return this.donationPointRepository.save(donationPoint);
  }
}
