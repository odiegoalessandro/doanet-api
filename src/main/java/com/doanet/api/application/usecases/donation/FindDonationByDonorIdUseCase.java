package com.doanet.api.application.usecases.donation;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonationRepository;
import com.doanet.api.domain.entities.donation.Donation;

public class FindDonationByDonorIdUseCase {
    private final DonationRepository donationRepository;

    public FindDonationByDonorIdUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public PageResponse<Donation> execute(Long donorId, int page, int size) {
        var pagination = new Pagination(page, size);

        return this.donationRepository.findByDonorId(donorId, pagination);
    }
}
