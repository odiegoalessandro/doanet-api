package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.commands.CreateOngCommand;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.application.usecases.user.GeolocateUserUseCase;
import com.doanet.api.domain.entities.ong.Ong;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;

public class CreateOngUseCase {
  private final OngRepository ongRepository;
  private final GeolocateUserUseCase geolocateUserUseCase;

  public CreateOngUseCase(OngRepository ongRepository, GeolocateUserUseCase geolocateUserUseCase){
    this.ongRepository = ongRepository;
    this.geolocateUserUseCase = geolocateUserUseCase;
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

    this.geolocateUserUseCase.execute(user);

    Ong ong = new Ong(null, user, ongCommand.cnpj());

    return this.ongRepository.save(ong);
  }
}
