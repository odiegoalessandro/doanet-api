package com.doanet.api.application.usecases;

import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.legacy.entity.Ong;

public class DeleteOngByIdUseCase {
  private final OngRepository ongRepository;

  public DeleteOngByIdUseCase(OngRepository ongRepository){
    this.ongRepository = ongRepository;
  }

  public void execute(Long id){
    this.ongRepository.deleteById(id);
  }
}
