package com.doanet.api.application.usecases;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;

public class FindActiveOngByCnpjUseCase {
  private final OngRepository ongRepository;

  public FindActiveOngByCnpjUseCase(OngRepository ongRepository){
    this.ongRepository = ongRepository;
  }

  public Ong execute(String cnpj){
    return this.ongRepository.findByCnpjActive(cnpj)
      .orElseThrow(() -> new ResourceNotFoundException("Não é possivel achar nenhuma ONG ativa com esse CNPJ"));
  }
}
