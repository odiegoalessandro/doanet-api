package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.commands.UpdateOngCommand;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;

public class UpdateOngUseCase {
  private final FindOngByIdUseCase findOngByIdUseCase;
  private final OngRepository ongRepository;

  public UpdateOngUseCase(FindOngByIdUseCase findOngByIdUseCase, OngRepository ongRepository){
    this.findOngByIdUseCase = findOngByIdUseCase;
    this.ongRepository = ongRepository;
  }

  public Ong execute(Long id, UpdateOngCommand ongCommand){
    var ong = this.findOngByIdUseCase.execute(id, true);

    ong.updateUserData(ongCommand.name(), ongCommand.email(), ongCommand.phone());

    return this.ongRepository.save(ong);
  }
}
