package com.doanet.api.legacy.service;

import com.doanet.api.legacy.entity.Donor;
import com.doanet.api.legacy.repository.DonorRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteDonorService {
  private FindDonorService findDonorService;
  private DonorRepository donorRepository;

  public DeleteDonorService(FindDonorService findDonorService, DonorRepository donorRepository){
    this.findDonorService = findDonorService;
    this.donorRepository = donorRepository;
  }

  public Donor delete(Long donorId){
    var donor = this.findDonorService.findById(donorId);

    donor.getUser().setActive(false);

    return this.donorRepository.save(donor);
  }

}
