package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.user.User;
import com.doanet.api.infra.persistence.UserEntity;

public class UserEntityMapper {
  public UserEntity toEntity(User user){
    return new UserEntity(
      user.getCity(),
      user.getEmail(),
      user.getNeighborhood(),
      user.getNumber(),
      user.getPassword(),
      user.getPhone(),
      user.getState(),
      user.getStreet(),
      user.getZipCode(),
      user.getName(),
      true
    );
  }

  public User toDomain(UserEntity userEntity){
    return new User(
      userEntity.getId(),
      userEntity.getName(),
      userEntity.getEmail(),
      userEntity.getPassword(),
      userEntity.getPhone(),
      userEntity.getStreet(),
      userEntity.getNumber(),
      userEntity.getNeighborhood(),
      userEntity.getCity(),
      userEntity.getState(),
      userEntity.getZipCode(),
      userEntity.getLatitude(),
      userEntity.getLongitude(),
      userEntity.getUserType(),
      userEntity.isActive()
    );
  }
}
