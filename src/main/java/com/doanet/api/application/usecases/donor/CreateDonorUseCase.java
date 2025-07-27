package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.commands.CreateDonorCommand;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.application.usecases.user.CreateUserUseCase;
import com.doanet.api.domain.entities.donor.Donor;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;

public class CreateDonorUseCase {
  private final DonorRepository donorRepository;
  private final CreateUserUseCase createUserUseCase;

  public CreateDonorUseCase(DonorRepository donorRepository, CreateUserUseCase createUserUseCase) {
    this.donorRepository = donorRepository;
    this.createUserUseCase = createUserUseCase;
  }

  public Donor execute(CreateDonorCommand donorCommand){
    var user = this.createUserUseCase.execute(new User(
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
    ));

    var donor = new Donor(null, user, donorCommand.document(), donorCommand.reasonSocial());

    return this.donorRepository.save(donor);
  }
}
