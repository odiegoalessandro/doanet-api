package com.doanet.api.infra.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaDonationPointRepository extends JpaRepository<DonationPointEntity, Long> {
    @Query("SELECT dp FROM DonationPoint dp WHERE id = :id AND dp.user.isActive = true")
    Optional<DonationPointEntity> findByIdActive(@Param("id") Long id);

    @Query(
      "SELECT dp FROM DonationPoint dp WHERE LOWER(dp.description) LIKE LOWER(CONCAT('%', :description, '%')) " +
        "AND dp.user.isActive = true"
    )
    Page<DonationPointEntity> findByDescriptionContainingIgnoreCaseActive(
      @Param("description") String description,
      Pageable pageable
    );

    @Query("SELECT dp FROM DonationPoint dp WHERE dp.user.isActive = true")
    Page<DonationPointEntity> findAllActive(Pageable pageable);
}
