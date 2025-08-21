package com.doanet.api.application.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;

import java.util.Optional;

public interface DonationPointRepository {
  DonationPoint save(DonationPoint donationPoint);
  Optional<DonationPoint> findById(Long id, boolean isActive);
  void deleteBy(Long id);
  PageResponse<DonationPoint> findByDescriptionIgnoreCase(String description, boolean isActive, Pagination pagination);
  PageResponse<DonationPoint> findAll(boolean isActive, Pagination pagination);

  void deleteById(Long id);
}
