package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.commands.CreateOngCommand;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.application.gateways.PasswordHasher;
import com.doanet.api.application.usecases.user.GeolocateUserUseCase;
import com.doanet.api.domain.entities.ong.Ong;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;

public class CreateOngUseCase {
  private final OngRepository ongRepository;
  private final GeolocateUserUseCase geolocateUserUseCase;
  private final PasswordHasher passwordHasher;

  public CreateOngUseCase(OngRepository ongRepository,
                          GeolocateUserUseCase geolocateUserUseCase,
                          PasswordHasher passwordHasher){
    this.ongRepository = ongRepository;
    this.geolocateUserUseCase = geolocateUserUseCase;
    this.passwordHasher = passwordHasher;
  }

  public Ong execute(CreateOngCommand ongCommand){
    var user = new User(
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
    );

    user.setPassword(this.passwordHasher.hash(ongCommand.password()));

    this.geolocateUserUseCase.execute(user);

    Ong ong = new Ong(null, user, ongCommand.cnpj());

    return this.ongRepository.save(ong);
  }
}
