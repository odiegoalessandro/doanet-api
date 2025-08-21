package com.doanet.api.infra.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaDonationPointRepository extends JpaRepository<DonationPointEntity, Long> {
    @Query("SELECT dp FROM DonationPointEntity dp WHERE id = :id AND dp.user.isActive = :isActive")
    Optional<DonationPointEntity> findById(@Param("id") Long id, @Param("isActive") boolean isActive);

    @Query(
      "SELECT dp FROM DonationPointEntity dp WHERE LOWER(dp.description) LIKE LOWER(CONCAT('%', :description, '%')) " +
        "AND dp.user.isActive = :isActive"
    )
    Page<DonationPointEntity> findByDescriptionIgnoreCase(
      @Param("description") String description,
      @Param("isActive") boolean isActive,
      Pageable pageable
    );

    @Query("SELECT dp FROM DonationPointEntity dp WHERE dp.user.isActive = :isActive")
    Page<DonationPointEntity> findAll(boolean isActive, Pageable pageable);
}
