package com.doanet.api.infra.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaDonorRepository extends JpaRepository<DonorEntity, Long> {
  @Query("SELECT d FROM DonorEntity d WHERE id = :id AND d.user.isActive = true")
  Optional<DonorEntity> findByIdActive(@Param("id") Long id);

  @Query("SELECT d FROM DonorEntity d WHERE d.document = :document AND d.user.isActive = true")
  Optional<DonorEntity> findByDocumentActive(@Param("document") String document);

  Optional<DonorEntity> findByDocument(String document);

  Page<DonorEntity> findByReasonSocialContainingIgnoreCase(String reasonSocial, Pageable pageable);

  @Query("SELECT d FROM DonorEntity d WHERE d.user.isActive = true")
  Page<DonorEntity> findAllActive(Pageable pageable);
}
