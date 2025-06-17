package com.doanet.api.service;

import com.doanet.api.client.GeolocationClient;
import com.doanet.api.dto.CoordinatesDto;
import com.doanet.api.entity.User;
import com.doanet.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserService {
  private final UserRepository userRepository;
  private final GeolocationClient geolocationClient;

  public CreateUserService(UserRepository userRepository, GeolocationClient geolocationClient){
    this.userRepository = userRepository;
    this.geolocationClient = geolocationClient;
  }

  @Transactional
  public void save(User user){
    if (user == null){
      throw new IllegalArgumentException("Usuário não pode ser nulo");
    }

    String address = user.buildAddress();
    CoordinatesDto coordinatesDto = this.geolocationClient.setCoordinatesByAddress(address);

    user.setLatitude(coordinatesDto.latitude());
    user.setLongitude(coordinatesDto.longitude());

    this.userRepository.save(user);
  }
}

