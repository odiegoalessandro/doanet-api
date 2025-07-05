package com.doanet.api.repository;

import com.doanet.api.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
  Page<Request> findByDonationPointId(Long donationPointId, Pageable pageable);
  Page<Request> findByOngId(Long ongId, Pageable pageable);
}
