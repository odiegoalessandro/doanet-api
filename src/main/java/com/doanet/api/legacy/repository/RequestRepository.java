package com.doanet.api.legacy.repository;

import com.doanet.api.legacy.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
  Page<Request> findByDonationPointId(Long donationPointId, Pageable pageable);
  Page<Request> findByOngId(Long ongId, Pageable pageable);
}
