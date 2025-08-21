package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;

public class FindAllDonationPointsUseCase {
  private final DonationPointRepository donationPointRepository;

  public FindAllDonationPointsUseCase(DonationPointRepository donationPointRepository) {
    this.donationPointRepository = donationPointRepository;
  }

  public PageResponse<DonationPoint> execute(int page, int size, boolean isActive){
    var pagination = new Pagination(page, size);

    return this.donationPointRepository.findAll(isActive, pagination);
  }
}
