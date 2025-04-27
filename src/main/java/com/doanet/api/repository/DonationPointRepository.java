package com.doanet.api.repository;

import com.doanet.api.entity.DonationPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationPointRepository extends JpaRepository<DonationPoint, Long> {
    Page<DonationPoint> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
}
