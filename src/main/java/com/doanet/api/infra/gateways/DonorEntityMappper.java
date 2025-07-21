package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.donor.Donor;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.infra.persistence.DonorEntity;

public class DonorEntityMappper {
  private final UserEntityMapper userMapper;

  public DonorEntityMappper(UserEntityMapper userMapper){
    this.userMapper = userMapper;
  }

  public DonorEntity toEntity(Donor donor){
    var userEntity = this.userMapper.toEntity(donor.getUser());


    return new DonorEntity(
      userEntity,
      donor.getDocument(),
      donor.getReasonSocial()
    );
  }

  public Donor toDomain(DonorEntity donorEntity){
    var user = this.userMapper.toDomain(donorEntity.getUser());

    return new Donor(
      donorEntity.getId(),
      user,
      donorEntity.getDocument(),
      donorEntity.getReasonSocial()
    );
  }
}
