package com.doanet.api.repository;

import com.doanet.api.entity.DonationPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationPointRepository extends JpaRepository<DonationPoint, Long> {
    @Query("SELECT dp FROM DonationPoint dp WHERE id = :id AND dp.user.isActive = true")
    DonationPoint findByIdActive(@Param("id") Long id);

    @Query(
      "SELECT dp FROM DonationPoint dp WHERE LOWER(dp.description) LIKE LOWER(CONCAT('%', :description, '%')) " +
        "AND dp.user.isActive = true"
    )
    Page<DonationPoint> findByDescriptionContainingIgnoreCaseActive(
      @Param("description") String description,
      Pageable pageable
    );

    @Query("SELECT dp FROM DonationPoint dp WHERE dp.user.isActive = true")
    Page<DonationPoint> findAllActive(Pageable pageable);
}
