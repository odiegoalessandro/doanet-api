package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.exceptions.ResourceNotFoundException;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;

public class FindOngByCnpjUseCase {
  private final OngRepository ongRepository;

  public FindOngByCnpjUseCase(OngRepository ongRepository){
    this.ongRepository = ongRepository;
  }

  public Ong execute(String cnpj, boolean isActive) {
    return this.ongRepository.findByCnpj(cnpj, isActive)
      .orElseThrow(() -> new ResourceNotFoundException("Não é possivel achar nenhuma ONG ativa com esse CNPJ"));
  }
}
