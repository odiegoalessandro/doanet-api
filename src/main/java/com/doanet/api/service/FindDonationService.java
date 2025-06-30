package com.doanet.api.service;

import com.doanet.api.dto.DetailedResponseDonationDto;
import com.doanet.api.dto.ResponseDonationDto;
import com.doanet.api.mapper.DonationMapper;
import com.doanet.api.repository.DonationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FindDonationService {
  private final DonationRepository donationRepository;
  private final DonationMapper donationMapper;

  public FindDonationService(DonationRepository donationRepository, DonationMapper donationMapper) {
    this.donationRepository = donationRepository;
    this.donationMapper = donationMapper;
  }

  public DetailedResponseDonationDto findDtoById(Long id) {
    var donation = this.donationRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("No donation found with id " + id));

    return donationMapper.toDetailedResponse(donation);
  }

  public Page<ResponseDonationDto> findAllDto(int pageNumber, int pageSize) {
    var pageable = PageRequest.of(pageNumber, pageSize);

    return this.donationRepository.findAll(pageable)
      .map(donationMapper::toResponse);
  }

  public Page<ResponseDonationDto> findByDonorIdDto(Long donorId, int pageNumber, int pageSize) {
    var pageable = PageRequest.of(pageNumber, pageSize);

    return this.donationRepository.findByDonorId(donorId, pageable)
      .map(donationMapper::toResponse);
  }

  public Page<ResponseDonationDto> findByDonationPointIdDto(Long donationPointId, int pageNumber, int pageSize) {
    var pageable = PageRequest.of(pageNumber, pageSize);

    return this.donationRepository.findByDonationPointId(donationPointId, pageable)
      .map(donationMapper::toResponse);
  }
}
