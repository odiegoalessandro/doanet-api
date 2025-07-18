package com.doanet.api.legacy.service;

import com.doanet.api.legacy.dto.UpdateDonationPointDto;
import com.doanet.api.legacy.entity.DonationPoint;
import com.doanet.api.legacy.mapper.UpdateDonationPointMapper;
import com.doanet.api.legacy.repository.DonationPointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateDonationPointService {
  private FindDonationPointService findDonationPointService;
  private DonationPointRepository donationPointRepository;
  private UpdateDonationPointMapper updateDonationPointMapper;


  public UpdateDonationPointService(
    FindDonationPointService findDonationPointService,
    DonationPointRepository donationPointRepository,
    UpdateDonationPointMapper updateDonationPointMapper
  ) {
    this.findDonationPointService = findDonationPointService;
    this.donationPointRepository = donationPointRepository;
    this.updateDonationPointMapper = updateDonationPointMapper;
  }

  @Transactional
  public DonationPoint update(Long donationPointId, UpdateDonationPointDto donationPointDto){
    var donationPoint = this.findDonationPointService.findById(donationPointId);

    this.updateDonationPointMapper.updateFromDto(donationPointDto, donationPoint);

    return this.donationPointRepository.save(donationPoint);
  }
}
