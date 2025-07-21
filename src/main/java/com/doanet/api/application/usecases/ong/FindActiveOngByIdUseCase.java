package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;
import com.doanet.api.infra.gateways.OngEntityMapper;

public class FindActiveOngByIdUseCase {
  private OngRepository ongRepository;
  private OngEntityMapper mapper;

  public FindActiveOngByIdUseCase(OngRepository ongRepository, OngEntityMapper mapper){
    this.ongRepository = ongRepository;
    this.mapper = mapper;
  }

  public Ong execute(Long id) {
    return this.ongRepository.findByIdActive(id)
      .orElseThrow(() -> new ResourceNotFoundException("Não é possivel achar uma ONG ativa com o id " + id));
  }
}
