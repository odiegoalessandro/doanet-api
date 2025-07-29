package com.doanet.api.application.usecases.donation;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonationRepository;
import com.doanet.api.domain.entities.donation.Donation;

public class FindDonationByDonationPointIdUseCase {
  private final DonationRepository donationRepository;

  public FindDonationByDonationPointIdUseCase(DonationRepository donationRepository) {
    this.donationRepository = donationRepository;
  }

  public PageResponse<Donation> execute(Long donationPointId, int page, int size) {
    var pagination = new Pagination(page, size);

    return this.donationRepository.findByDonationPointId(donationPointId, pagination);
  }
}
