package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.donationpoint.DonationPoint;
import com.doanet.api.infra.persistence.DonationPointEntity;

public class DonationPointEntityMapper {
  private final UserEntityMapper userMapper;

  public DonationPointEntityMapper(UserEntityMapper userMapper) {
    this.userMapper = userMapper;
  }

  public DonationPointEntity toEntity(DonationPoint donationPoint){
    var userEntity = this.userMapper.toEntity(donationPoint.getUser());

    return new DonationPointEntity(
      donationPoint.getId(),
      userEntity,
      donationPoint.getDescription()
    );
  }

  public DonationPoint toDomain(DonationPointEntity donationPoint){
    var user = this.userMapper.toDomain(donationPoint.getUser());

    return new DonationPoint(
      donationPoint.getId(),
      user,
      donationPoint.getDescription()
    );
  }
}
