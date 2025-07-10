package com.doanet.api.service;

import com.doanet.api.dto.UpdateDonationPointDto;
import com.doanet.api.entity.DonationPoint;
import com.doanet.api.mapper.UpdateDonationPointMapper;
import com.doanet.api.repository.DonationPointRepository;
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
