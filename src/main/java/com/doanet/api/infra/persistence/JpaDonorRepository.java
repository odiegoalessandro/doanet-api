package com.doanet.api.infra.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaDonorRepository extends JpaRepository<DonorEntity, Long> {
  @Query("SELECT d FROM DonorEntity d WHERE id = :id AND d.user.isActive = :isActive")
  Optional<DonorEntity> findById(@Param("id") Long id, @Param("isActive") boolean isActive);

  @Query("SELECT d FROM DonorEntity d WHERE d.document = :document AND d.user.isActive = :isActive")
  Optional<DonorEntity> findByDocument(@Param("document") String document, @Param("isActive") boolean isActive);

  @Query(
    "SELECT d FROM DonorEntity d " +
      "WHERE LOWER(d.reasonSocial) LIKE LOWER(CONCAT('%', :reasonSocial, '%')) " +
      "AND d.user.isActive = :isActive"
  )
  Page<DonorEntity> findByReasonSocialIgnoreCase(
    String reasonSocial,
    Pageable pageable,
    @Param("isActive") boolean isActive
  );

  @Query("SELECT d FROM DonorEntity d WHERE d.user.isActive = :isActive")
  Page<DonorEntity> findAll(Pageable pageable, @Param("isActive") boolean isActive);
}
