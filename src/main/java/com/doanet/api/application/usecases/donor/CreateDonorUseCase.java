package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.commands.CreateDonorCommand;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.application.gateways.PasswordHasher;
import com.doanet.api.application.usecases.user.GeolocateUserUseCase;
import com.doanet.api.domain.entities.donor.Donor;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;

public class CreateDonorUseCase {
  private final DonorRepository donorRepository;
  private final GeolocateUserUseCase geolocateUserUseCase;
  private final PasswordHasher passwordHasher;

  public CreateDonorUseCase(DonorRepository donorRepository,
                            GeolocateUserUseCase geolocateUserUseCase,
                            PasswordHasher passwordHasher) {
    this.donorRepository = donorRepository;
    this.geolocateUserUseCase = geolocateUserUseCase;
    this.passwordHasher = passwordHasher;
  }

  public Donor execute(CreateDonorCommand donorCommand){
    var user = new User(
      null,
      donorCommand.name(),
      donorCommand.email(),
      donorCommand.password(),
      donorCommand.phone(),
      donorCommand.street(),
      donorCommand.number(),
      donorCommand.neighborhood(),
      donorCommand.city(),
      donorCommand.state(),
      donorCommand.zipCode(),
      null,
      null,
      UserType.DONOR,
      true
    );

    user.setPassword(this.passwordHasher.hash(donorCommand.password()));

    this.geolocateUserUseCase.execute(user);

    var donor = new Donor(null, user, donorCommand.document(), donorCommand.reasonSocial());

    return this.donorRepository.save(donor);
  }
}
