package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.commands.UpdateOngCommand;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;

public class UpdateOngUseCase {
  private final FindActiveOngByIdUseCase findActiveOngByIdUseCase;
  private final OngRepository ongRepository;

  public UpdateOngUseCase(FindActiveOngByIdUseCase findActiveOngByIdUseCase, OngRepository ongRepository){
    this.findActiveOngByIdUseCase = findActiveOngByIdUseCase;
    this.ongRepository = ongRepository;
  }

  public Ong execute(Long id, UpdateOngCommand ongCommand){
    var ong = this.findActiveOngByIdUseCase.execute(id);

    ong.updateUserData(ongCommand.name(), ongCommand.email(), ongCommand.phone());

    return this.ongRepository.save(ong);
  }
}
