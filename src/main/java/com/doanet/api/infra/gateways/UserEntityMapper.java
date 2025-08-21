package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.user.User;
import com.doanet.api.infra.persistence.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {
  public UserEntity toEntity(User user){
    return new UserEntity(
      user.getId(),
      user.getName(),
      user.getEmail(),
      user.getPassword(),
      user.getPhone(),
      user.getStreet(),
      user.getNumber(),
      user.getNeighborhood(),
      user.getCity(),
      user.getState(),
      user.getZipCode(),
      user.getLatitude(),
      user.getLongitude(),
      user.getUserType(),
      user.isActive()
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
