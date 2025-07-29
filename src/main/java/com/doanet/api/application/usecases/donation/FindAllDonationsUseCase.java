package com.doanet.api.application.usecases.donation;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonationRepository;
import com.doanet.api.domain.entities.donation.Donation;

public class FindAllDonationsUseCase {
  private final DonationRepository donationRepository;

  public FindAllDonationsUseCase(DonationRepository donationRepository) {
    this.donationRepository = donationRepository;
  }

  public PageResponse<Donation> execute(int page, int size) {
    var pagination = new Pagination(page, size);

    return this.donationRepository.findAll(pagination);
  }
}
