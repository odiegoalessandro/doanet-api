package com.doanet.api.application.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.domain.entities.donor.Donor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DonorRepository {
  Donor save(Donor donor);
  Optional<Donor> findById(@Param("id") Long id, boolean isActive);
  Optional<Donor> findByDocument(@Param("document") String document, boolean isActive);
  void deleteById(Long id);
  PageResponse<Donor> findAll(Pagination pagination, boolean isActive);
  PageResponse<Donor> findByReasonSocialIgnoreCase(String reasonSocial, Pagination pagination, boolean isActive);
}