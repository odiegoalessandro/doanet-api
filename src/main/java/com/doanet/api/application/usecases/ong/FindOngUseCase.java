package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;

public class FindOngUseCase {
  private final OngRepository ongRepository;

  public FindOngUseCase(OngRepository ongRepository) {
    this.ongRepository = ongRepository;
  }

  public PageResponse<Ong> execute(int page, int size, boolean isActive) {
    var pagination = new Pagination(page, size);

    return this.ongRepository.findAll(pagination, isActive);
  }
}
