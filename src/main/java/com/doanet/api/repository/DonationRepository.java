package com.doanet.api.repository;

import com.doanet.api.entity.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
  Page<Donation> findByDonorId(Long donorId, Pageable pageable);
  Page<Donation> findByDonationPointId(Long donationPointId, Pageable pageable);
}
