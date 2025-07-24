package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.donation.DonationItem;
import com.doanet.api.infra.persistence.DonationItemEntity;

public class DonationItemEntityMapper {
  private final DonationItemMapper donorMapper;
  private final ItemEntityMapper itemMapper;

  public DonationItemEntityMapper(DonationItemMapper donorMapper, ItemEntityMapper itemMapper) {
    this.donorMapper = donorMapper;
    this.itemMapper = itemMapper;
  }

  public DonationItemEntity toEntity(DonationItem donationItem){
    return new DonationItemEntity(
      donationItem.getId(),
      donationItem.getQuantity(),
      this.itemMapper.toEntity(donationItem.getItem()),
      this.donorMapper.toEntity(donationItem.getDonation())
    );
  }

  public DonationItem toDomain(DonationItemEntity donationItemEntity) {
    return new DonationItem(
      donationItemEntity.getId(),
      donationItemEntity.getQuantity(),
      this.itemMapper.toDomain(donationItemEntity.getItem()),
      this.donorMapper.toDomain(donationItemEntity.getDonationEntity())
    );
  }
}
