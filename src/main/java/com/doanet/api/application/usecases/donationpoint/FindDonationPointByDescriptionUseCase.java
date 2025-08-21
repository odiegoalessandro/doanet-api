package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;

public class FindDonationPointByDescriptionUseCase {
  private final DonationPointRepository donationPointRepository;

  public FindDonationPointByDescriptionUseCase(DonationPointRepository donationPointRepository) {
    this.donationPointRepository = donationPointRepository;
  }

  public PageResponse<DonationPoint> execute(String description, int page, int size, boolean isActive) {
    var pagination = new Pagination(page, size);

    return this.donationPointRepository.findByDescriptionIgnoreCase(description, isActive, pagination);
  }
}
