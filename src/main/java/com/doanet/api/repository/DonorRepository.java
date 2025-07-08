package com.doanet.api.repository;

import com.doanet.api.entity.Donor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
  @Query("SELECT d FROM Donor d WHERE id = :id AND d.user.isActive = true")
  Optional<Donor> findByIdActive(@Param("id") Long id);

  @Query("SELECT d FROM Donor d WHERE d.document = :document AND d.user.isActive = true")
  Optional<Donor> findByDocumentActive(@Param("document") String document);

  Optional<Donor> findByDocument(String document);

  @Query("SELECT d FROM Donor d WHERE LOWER(d.reasonSocial) = :reasonSocial AND d.user.isActive = true")
  Optional<Donor> findByReasonSocialIgnoreCaseActive(@Param("reasonSocial") String reasonSocial);

  Optional<Donor> findByReasonSocialIgnoreCase(String reasonSocial);

  @Query("SELECT d FROM Donor d WHERE d.user.isActive = true")
  Page<Donor> findAllActive(Pageable pageable);
}
