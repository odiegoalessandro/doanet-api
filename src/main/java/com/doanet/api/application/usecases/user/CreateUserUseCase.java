package com.doanet.api.application.usecases.user;

import com.doanet.api.application.gateways.GetCoordinatesByAddress;
import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.domain.entities.user.User;

public class CreateUserUseCase {
  private final UserRepository userRepository;
  private final GetCoordinatesByAddress getCoordinatesByAddress;


  public CreateUserUseCase(UserRepository userRepository, GetCoordinatesByAddress getCoordinatesByAddress) {
    this.userRepository = userRepository;
    this.getCoordinatesByAddress = getCoordinatesByAddress;
  }

  public User execute(User user){
    var coordinates = this.getCoordinatesByAddress.execute(user.buildAddress());

    user.setLatitude(coordinates.latitude());
    user.setLongitude(coordinates.longitude());

    return this.userRepository.save(user);
  }
}
