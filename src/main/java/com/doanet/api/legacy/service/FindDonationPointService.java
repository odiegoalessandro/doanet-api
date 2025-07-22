package com.doanet.api.legacy.service;

import com.doanet.api.legacy.entity.DonationPointEntity;
import com.doanet.api.legacy.repository.DonationPointRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FindDonationPointService {
  private final DonationPointRepository donationPointRepository;

  public FindDonationPointService(DonationPointRepository donationPointRepository) {
    this.donationPointRepository = donationPointRepository;
  }

  public DonationPointEntity findById(Long id){
    return this.donationPointRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Donation point no found with id " + id));
  }

  public Page<DonationPointEntity> findByDescription(String description, Integer pageNumber, Integer pageSize){
    var pageable = PageRequest.of(pageNumber, pageSize);

    return this.donationPointRepository.findByDescriptionContainingIgnoreCaseActive(description, pageable);
  }

  public Page<DonationPointEntity> findAll(Integer pageNumber, Integer pageSize) {
    var pageable = PageRequest.of(pageNumber, pageSize);

    return this.donationPointRepository.findAll(pageable);
  }
}
