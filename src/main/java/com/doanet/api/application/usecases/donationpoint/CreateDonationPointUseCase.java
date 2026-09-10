package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.commands.CreateDonationPointCommand;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.application.gateways.PasswordHasher;
import com.doanet.api.application.usecases.user.GeolocateUserUseCase;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;

public class CreateDonationPointUseCase {
  private final DonationPointRepository donationPointRepository;
  private final GeolocateUserUseCase geolocateUserUseCase;
  private final PasswordHasher passwordHasher;

  public CreateDonationPointUseCase(
    DonationPointRepository donationPointRepository,
    GeolocateUserUseCase geolocateUserUseCase,
    PasswordHasher passwordHasher
  ) {
    this.donationPointRepository = donationPointRepository;
    this.geolocateUserUseCase = geolocateUserUseCase;
    this.passwordHasher = passwordHasher;
  }

  public DonationPoint execute(CreateDonationPointCommand donationPointCommand){
    var user = new User(
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
      null,
      null,
      UserType.DONATION_POINT,
      true
    );

    user.setPassword(this.passwordHasher.hash(donationPointCommand.password()));

    this.geolocateUserUseCase.execute(user);

    var donationPoint = new DonationPoint(null, user, donationPointCommand.description());

    return this.donationPointRepository.save(donationPoint);
  }
}
