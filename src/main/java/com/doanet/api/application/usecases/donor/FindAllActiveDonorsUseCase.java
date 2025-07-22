package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.domain.entities.donor.Donor;

public class FindAllActiveDonorsUseCase {
  private final DonorRepository donorRepository;

  public FindAllActiveDonorsUseCase(DonorRepository donorRepository){
    this.donorRepository = donorRepository;
  }

  public PageResponse<Donor> execute(int page, int size){
    var pagination = new Pagination(page, size);

    return this.donorRepository.findAllActive(pagination);
  }
}
