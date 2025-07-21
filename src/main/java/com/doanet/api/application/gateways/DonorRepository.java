package com.doanet.api.application.gateways;

import com.doanet.api.domain.entities.donor.Donor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DonorRepository {
  Donor save(Donor donor);
  Optional<Donor> findByIdActive(@Param("id") Long id);
  Optional<Donor> findByDocumentActive(@Param("document") String document);
  Optional<Donor> findByDocument(String document);
  Optional<Donor> findByReasonSocialIgnoreCaseActive(@Param("reasonSocial") String reasonSocial);
  Optional<Donor> findByReasonSocialIgnoreCase(String reasonSocial);
  void deleteById(Long id);

//  Page<Donor> findAllActive(Pageable pageable);
}
