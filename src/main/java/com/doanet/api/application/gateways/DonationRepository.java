package com.doanet.api.application.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.domain.entities.donation.Donation;
import com.doanet.api.domain.enums.DonationStatus;

import java.util.Optional;

public interface DonationRepository {
  Donation save(Donation donation);
  Optional<Donation> findById(Long id);
  PageResponse<Donation> findByDonorId(Long donorId, Pagination pagination);
  PageResponse<Donation> findByDonationPointId(Long donationPointId, Pagination pagination);
  PageResponse<Donation> findAll(Pagination pagination);
  PageResponse<Donation> findByStatus(DonationStatus status, Pagination pagination);
}
