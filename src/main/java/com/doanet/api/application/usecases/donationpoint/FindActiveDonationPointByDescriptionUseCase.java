package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;

public class FindActiveDonationPointByDescriptionUseCase {
  private final DonationPointRepository donationPointRepository;

  public FindActiveDonationPointByDescriptionUseCase(DonationPointRepository donationPointRepository) {
    this.donationPointRepository = donationPointRepository;
  }

  public PageResponse<DonationPoint> execute(String description, int page, int size){
    var pagination = new Pagination(page, size);

    return this.donationPointRepository.findByDescriptionContainingIgnoreCaseActive(description, pagination);
  }
}
