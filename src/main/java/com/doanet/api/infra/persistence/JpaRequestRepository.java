package com.doanet.api.infra.persistence;

import com.doanet.api.domain.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRequestRepository extends JpaRepository<RequestEntity, Long> {
  Page<RequestEntity> findByDonationPointId(Long donationPointId, Pageable pageable);
  Page<RequestEntity> findByOngId(Long ongId, Pageable pageable);
  Page<RequestEntity> findByStatus(RequestStatus status, Pageable pageable);
}
