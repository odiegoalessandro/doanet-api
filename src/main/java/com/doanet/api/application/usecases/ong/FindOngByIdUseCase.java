package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;

public class FindOngByIdUseCase {
  private final OngRepository ongRepository;

  public FindOngByIdUseCase(OngRepository ongRepository){
    this.ongRepository = ongRepository;
  }

  public Ong execute(Long id, boolean isActive) {
    return this.ongRepository.findById(id, isActive)
      .orElseThrow(() -> new ResourceNotFoundException("Não é possivel achar uma ONG ativa com o id " + id));
  }
}
