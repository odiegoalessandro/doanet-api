package com.doanet.api.service;

import com.doanet.api.entity.Donor;
import com.doanet.api.repository.DonorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FindDonorService {
  private final DonorRepository donorRepository;

  public FindDonorService(DonorRepository donorRepository) {
    this.donorRepository = donorRepository;
  }

  public Donor findById(Long id){
    return this.donorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No donor found with id " + id));
  }

  public Donor findByDocument(String document){
    return this.donorRepository.findByDocument(document)
        .orElseThrow(() -> new EntityNotFoundException("No donor found with document " + document));
  }

  public Donor findByReasonSocial(String reasonSocial){
    return this.donorRepository.findByReasonSocialIgnoreCase(reasonSocial)
        .orElseThrow(() -> new EntityNotFoundException("No donor found with reason social " + reasonSocial));
  }

  public Page<Donor> findAll(int pageNumber, int pageSize){
    var pageable = PageRequest.of(pageNumber, pageSize);

    return this.donorRepository.findAll(pageable);
  }
}
