package com.doanet.api.legacy.service;

import com.doanet.api.legacy.dto.CreateDonationDto;
import com.doanet.api.legacy.dto.CreateDonationItemDto;
import com.doanet.api.legacy.dto.ResponseDonationDto;
import com.doanet.api.legacy.entity.Donation;
import com.doanet.api.legacy.entity.DonationItem;
import com.doanet.api.legacy.mapper.DonationMapper;
import com.doanet.api.legacy.repository.DonationItemRepository;
import com.doanet.api.legacy.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CreateDonationService {
  private DonationRepository donationRepository;
  private DonationItemRepository donationItemRepository;
  private FindDonorService findDonorService;
  private FindDonationPointService findDonationPointService;
  private FindItemService findItemService;
  private DonationMapper donationMapper;

  public CreateDonationService(
    DonationRepository donationRepository,
    DonationItemRepository donationItemRepository,
    FindDonorService findDonorService,
    FindDonationPointService findDonationPointService,
    FindItemService findItemService,
    DonationMapper donationMapper
  ){
    this.donationRepository = donationRepository;
    this.donationItemRepository = donationItemRepository;
    this.findDonorService = findDonorService;
    this.findDonationPointService = findDonationPointService;
    this.findItemService = findItemService;
    this.donationMapper = donationMapper;
  }

  @Transactional
  public ResponseDonationDto create(CreateDonationDto createDonationDto) {
    var donor = findDonorService.findById(createDonationDto.donorId());
    var donationPoint = findDonationPointService.findById(createDonationDto.donationPointId());
    var donation = new Donation(donor, donationPoint, LocalDate.now());

    donationRepository.save(donation);

    for (CreateDonationItemDto itemDto : createDonationDto.items()) {
      var item = findItemService.findById(itemDto.itemId());
      var donationItem = new DonationItem(item, donation, itemDto.quantity());

      donationItemRepository.save(donationItem);
      donation.getDonationItems().add(donationItem);
    }

    return donationMapper.toResponse(donation);
  }
}
