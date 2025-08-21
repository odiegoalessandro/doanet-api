package com.doanet.api.application.usecases.donation;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonationRepository;
import com.doanet.api.domain.entities.donation.Donation;
import com.doanet.api.domain.enums.DonationStatus;

public class FindDonationByStatusUseCase {
  private final DonationRepository donationRepository;

  public FindDonationByStatusUseCase(DonationRepository donationRepository) {
    this.donationRepository = donationRepository;
  }

  public PageResponse<Donation> execute(DonationStatus status, int page, int size) {
    var pagination = new Pagination(page, size);

    return this.donationRepository.findByStatus(status, pagination);
  }
}
