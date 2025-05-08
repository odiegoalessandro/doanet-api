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
    updateAddress(user);

    this.userRepository.save(user);
  }


  public void updateAddress(User user){
    String address = user.buildAddress();
    CoordinetesDto response = geolocationClient.getCoordinetes(address);

    user.setLatitude(response.latitude());
    user.setLatitude(response.longitude());
  }
}

