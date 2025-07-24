package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.donation.Donation;
import com.doanet.api.domain.enums.DonationStatus;
import com.doanet.api.infra.persistence.DonationEntity;

import java.time.LocalDate;

public class DonationItemMapper {
  private final DonorEntityMappper donorMapper;
  private final DonationPointEntityMapper donationPointMapper;
  private final DonationItemEntityMapper donationItemMapper;


  public DonationItemMapper(DonorEntityMappper donorMapper, DonationPointEntityMapper donationPointMapper,
                            DonationItemEntityMapper donationItemMapper) {
    this.donorMapper = donorMapper;
    this.donationPointMapper = donationPointMapper;
    this.donationItemMapper = donationItemMapper;
  }

  public DonationEntity toEntity(Donation donation) {
    var donorEntity = this.donorMapper.toEntity(donation.getDonor());
    var donationPointEntity = this.donationPointMapper.toEntity(donation.getDonationPoint());
    var donationEntity = new DonationEntity(
      null,
      donorEntity,
      donationPointEntity,
      LocalDate.now(),
      null,
      DonationStatus.CREATED
    );

    var itemEntities = donation.getDonationItems()
      .stream()
      .map(donationItemMapper::toEntity)
      .toList();

    itemEntities.forEach(item -> item.setDonationEntity(donationEntity));
    donationEntity.setDonationItems(itemEntities);

    return donationEntity;
  }

  public Donation toDomain(DonationEntity donationEntity) {
    var donor = this.donorMapper.toDomain(donationEntity.getDonor());
    var donationPoint = this.donationPointMapper.toDomain(donationEntity.getDonationPoint());

    var donation = new Donation(
      null,
      donor,
      donationPoint,
      LocalDate.now(),
      null,
      DonationStatus.CREATED
    );

    var donationItems = donationEntity.getDonationItems()
      .stream()
      .map(donationItemMapper::toDomain)
      .toList();

    donationItems.forEach(item -> item.setDonation(donation));
    donation.setDonationItems(donationItems);

    return donation;
  }
}
