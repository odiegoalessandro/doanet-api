package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.gateways.OngRepository;

public class DeleteOngByIdUseCase {
  private final OngRepository ongRepository;

  public DeleteOngByIdUseCase(OngRepository ongRepository){
    this.ongRepository = ongRepository;
  }

  public void execute(Long id){
    this.ongRepository.deleteById(id);
  }
}
