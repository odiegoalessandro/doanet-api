package com.doanet.api.service;

import com.doanet.api.entity.DonationPoint;
import com.doanet.api.repository.DonationPointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteDonationPointService {
  private FindDonationPointService findDonationPointService;
  private DonationPointRepository donationPointRepository;

  public DeleteDonationPointService(
    FindDonationPointService findDonationPointService,
    DonationPointRepository donationPointRepository
  ){
    this.findDonationPointService = findDonationPointService;
    this.donationPointRepository = donationPointRepository;
  }

  @Transactional
  public DonationPoint delete(Long donationPointId){
    var donationPoint = this.findDonationPointService.findById(donationPointId);

    donationPoint.getUser().setActive(false);

    return this.donationPointRepository.save(donationPoint);
  }
}
