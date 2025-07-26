package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.donation.Donation;
import com.doanet.api.infra.persistence.DonationEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DonationEntityMapper {
  private final DonorEntityMappper donorMapper;
  private final DonationPointEntityMapper donationPointMapper;
  private final DonationItemEntityMapper donationItemMapper;

  public DonationEntityMapper(DonorEntityMappper donorMapper, DonationPointEntityMapper donationPointMapper,
                              DonationItemEntityMapper donationItemMapper) {
    this.donorMapper = donorMapper;
    this.donationPointMapper = donationPointMapper;
    this.donationItemMapper = donationItemMapper;
  }

  public DonationEntity toEntity(Donation donation) {
    var donationEntity = new DonationEntity(
      donation.getId(),
      donorMapper.toEntity(donation.getDonor()),
      donationPointMapper.toEntity(donation.getDonationPoint()),
      donation.getCreatedAt(),
      null,
      donation.getStatus()
    );

    var items = donation.getDonationItems().stream()
      .map(item -> {
        var entity = donationItemMapper.toEntity(item);
        entity.setDonationEntity(donationEntity);

        return entity;
      })
      .toList();

    donationEntity.setDonationItems(items);
    return donationEntity;
  }


  public Donation toDomain(DonationEntity entity) {
    var donation = new Donation(
      entity.getId(),
      this.donorMapper.toDomain(entity.getDonor()),
      this.donationPointMapper.toDomain(entity.getDonationPoint()),
      entity.getCreatedAt(),
      null,
      entity.getStatus()
    );

    var items = entity.getDonationItems().stream().map(item -> {
      var entityItem = donationItemMapper.toDomain(item);
      entityItem.setDonation(donation);

      return entityItem;
    }).toList();

    donation.setDonationItems(items);
    return donation;
  }
}
