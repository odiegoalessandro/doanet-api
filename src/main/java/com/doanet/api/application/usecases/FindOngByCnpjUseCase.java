package com.doanet.api.application.usecases;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;

public class FindOngByCnpjUseCase {
  private final OngRepository ongRepository;

  public FindOngByCnpjUseCase(OngRepository ongRepository){
    this.ongRepository = ongRepository;
  }

  public Ong execute(String cnpj){
    return this.ongRepository.findByCnpj(cnpj)
      .orElseThrow(() -> new ResourceNotFoundException("Não foi possivel achar uma ONG com este cnpj"));
  }
}
