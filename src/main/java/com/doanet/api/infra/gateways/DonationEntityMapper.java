package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.donation.Donation;
import com.doanet.api.infra.persistence.DonationEntity;

import java.time.LocalDate;

public class DonationEntityMapper {
  private final DonorEntityMappper donorMapper;
  private final DonationPointEntityMapper donationPointMapper;

  public DonationEntityMapper(DonorEntityMappper donorMapper, DonationPointEntityMapper donationPointMapper) {
    this.donorMapper = donorMapper;
    this.donationPointMapper = donationPointMapper;
  }

  public DonationEntity toEntity(Donation donation) {
    return new DonationEntity(
      donation.getId(),
      this.donorMapper.toEntity(donation.getDonor()),
      this.donationPointMapper.toEntity(donation.getDonationPoint()),
      LocalDate.now(),
      null,
      donation.getStatus()
    );
  }

  public Donation toDomain(DonationEntity entity) {
    return new Donation(
      entity.getId(),
      this.donorMapper.toDomain(entity.getDonor()),
      this.donationPointMapper.toDomain(entity.getDonationPoint()),
      entity.getCreatedAt(),
      null,
      entity.getStatus()
    );
  }
}
