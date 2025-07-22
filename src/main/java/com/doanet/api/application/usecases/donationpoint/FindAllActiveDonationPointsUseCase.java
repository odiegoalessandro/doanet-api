package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;

public class FindAllActiveDonationPointsUseCase {
  private final DonationPointRepository donationPointRepository;

  public FindAllActiveDonationPointsUseCase(DonationPointRepository donationPointRepository) {
    this.donationPointRepository = donationPointRepository;
  }

  public PageResponse<DonationPoint> execute(int page, int size){
    var pagination = new Pagination(page, size);

    return this.donationPointRepository.findAllActive(pagination);
  }
}
