package com.doanet.api.service;

import com.doanet.api.dto.CreateDonationDto;
import com.doanet.api.dto.CreateDonationItemDto;
import com.doanet.api.dto.ResponseDonationDto;
import com.doanet.api.entity.Donation;
import com.doanet.api.entity.DonationItem;
import com.doanet.api.mapper.DonationMapper;
import com.doanet.api.repository.DonationItemRepository;
import com.doanet.api.repository.DonationRepository;
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
