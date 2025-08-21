package com.doanet.api.infra.persistence;

import com.doanet.api.domain.enums.DonationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDonationRepository extends JpaRepository<DonationEntity, Long> {
  Page<DonationEntity> findByDonorId(Long donorId, Pageable pageable);
  Page<DonationEntity> findByDonationPointId(Long donationPointId, Pageable pageable);
  Page<DonationEntity> findByStatus(DonationStatus status, PageRequest pageable);
}
