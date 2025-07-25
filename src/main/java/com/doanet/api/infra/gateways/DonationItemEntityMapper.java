package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.donation.DonationItem;
import com.doanet.api.infra.persistence.DonationItemEntity;

public class DonationItemEntityMapper {
  private final ItemEntityMapper itemMapper;

  public DonationItemEntityMapper(ItemEntityMapper itemMapper) {
    this.itemMapper = itemMapper;
  }

  public DonationItemEntity toEntity(DonationItem donationItem){
    return new DonationItemEntity(
      donationItem.getId(),
      donationItem.getQuantity(),
      this.itemMapper.toEntity(donationItem.getItem()),
      null
    );
  }

  public DonationItem toDomain(DonationItemEntity donationItemEntity) {
    return new DonationItem(
      donationItemEntity.getId(),
      donationItemEntity.getQuantity(),
      this.itemMapper.toDomain(donationItemEntity.getItem()),
      null
    );
  }
}
