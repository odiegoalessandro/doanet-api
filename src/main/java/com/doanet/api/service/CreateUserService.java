package com.doanet.api.service;

import com.doanet.api.client.GeolocationClient;
import com.doanet.api.dto.CoordinetesDto;
import com.doanet.api.entity.User;
import com.doanet.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
  private final UserRepository userRepository;
  private final GeolocationClient geolocationClient;

  public CreateUserService(UserRepository userRepository, GeolocationClient geolocationClient){
    this.userRepository = userRepository;
    this.geolocationClient = geolocationClient;
  }

  public void save(User user){
    String address = user.buildAddress();
    CoordinetesDto coordinetesDto = this.geolocationClient.setCoordinatesByAddress(address);

    user.setLatitude(coordinetesDto.latitude());
    user.setLongitude(coordinetesDto.longitude());

    this.userRepository.save(user);
  }
}

