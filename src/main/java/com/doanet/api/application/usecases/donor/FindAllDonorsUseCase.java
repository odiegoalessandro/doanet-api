package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.domain.entities.donor.Donor;

public class FindAllDonorsUseCase {
  private final DonorRepository donorRepository;

  public FindAllDonorsUseCase(DonorRepository donorRepository){
    this.donorRepository = donorRepository;
  }

  public PageResponse<Donor> execute(int page, int size, boolean isActive){
    var pagination = new Pagination(page, size);

    return this.donorRepository.findAll(pagination, isActive);
  }
}
