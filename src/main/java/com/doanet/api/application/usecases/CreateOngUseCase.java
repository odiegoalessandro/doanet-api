package com.doanet.api.application.usecases;

import com.doanet.api.application.commands.CreateOngCommand;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.legacy.enums.UserType;

public class CreateOngUseCase {
  private final OngRepository ongRepository;
  private final CreateUserUseCase createUserUseCase;

  public CreateOngUseCase(OngRepository ongRepository, CreateUserUseCase createUserUseCase){
    this.ongRepository = ongRepository;
    this.createUserUseCase = createUserUseCase;
  }

  public Ong execute(CreateOngCommand ongCommand){
    var user = this.createUserUseCase.execute(
      new User(
        null,
        ongCommand.name(),
        ongCommand.email(),
        ongCommand.password(),
        ongCommand.phone(),
        ongCommand.street(),
        ongCommand.number(),
        ongCommand.neighborhood(),
        ongCommand.city(),
        ongCommand.state(),
        ongCommand.zipCode(),
        null,
        null,
        UserType.ONG,
        true
      )
    );

    Ong ong = new Ong(null, user, ongCommand.cnpj());

    return this.ongRepository.save(ong);
  }
}
