package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.commands.CreateDonationPointCommand;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.application.usecases.user.CreateUserUseCase;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.legacy.enums.UserType;

public class CreateDonationPointUseCase {
  private final DonationPointRepository donationPointRepository;
  private final CreateUserUseCase createUserUseCase;

  public CreateDonationPointUseCase(DonationPointRepository donationPointRepository,
                                    CreateUserUseCase createUserUseCase) {
    this.donationPointRepository = donationPointRepository;
    this.createUserUseCase = createUserUseCase;
  }

  public DonationPoint execute(CreateDonationPointCommand donationPointCommand){
    var user = this.createUserUseCase.execute(new User(
      null,
      donationPointCommand.name(),
      donationPointCommand.email(),
      donationPointCommand.password(),
      donationPointCommand.phone(),
      donationPointCommand.street(),
      donationPointCommand.number(),
      donationPointCommand.neighborhood(),
      donationPointCommand.city(),
      donationPointCommand.state(),
      donationPointCommand.zipCode(),
      donationPointCommand.latitude(),
      donationPointCommand.longitude(),
      UserType.DONATION_POINT,
      true
    ));

    var donationPoint = new DonationPoint(null, user, donationPointCommand.description());

    return this.donationPointRepository.save(donationPoint);
  }
}
