package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;

public class FindAllActiveOngUseCase {
  private final OngRepository ongRepository;

  public FindAllActiveOngUseCase(OngRepository ongRepository) {
    this.ongRepository = ongRepository;
  }

  public PageResponse<Ong> execute(int page, int size){
    var pagination = new Pagination(page, size);

    return this.ongRepository.findAllActive(pagination);
  }
}
