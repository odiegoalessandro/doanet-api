package com.doanet.api.application.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;

import java.util.Optional;

public interface DonationPointRepository {
  DonationPoint save(DonationPoint donationPoint);
  Optional<DonationPoint> findByIdActive(Long id);
  void deleteBy(Long id);
  PageResponse<DonationPoint> findByDescriptionContainingIgnoreCaseActive(String description, Pagination pagination);
  PageResponse<DonationPoint> findAllActive(Pagination pagination);
}
