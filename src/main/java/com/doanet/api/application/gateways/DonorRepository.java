package com.doanet.api.application.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.domain.entities.donor.Donor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DonorRepository {
  Donor save(Donor donor);
  Optional<Donor> findByIdActive(@Param("id") Long id);
  Optional<Donor> findByDocumentActive(@Param("document") String document);
  Optional<Donor> findByDocument(String document);
  void deleteById(Long id);
  PageResponse<Donor> findAllActive(Pagination pagination);
  PageResponse<Donor> findByReasonSocialContainingIgnoreCase(String reasonSocial, Pagination pagination);
}
