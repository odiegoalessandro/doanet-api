package com.doanet.api.service;

import com.doanet.api.dto.CreateUserDto;
import com.doanet.api.entity.DonationPoint;
import com.doanet.api.entity.User;
import com.doanet.api.enums.UserType;
import com.doanet.api.repository.DonationPointRepository;
import com.doanet.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateDonationPointService {
  private final CreateUserService createUserService;
  private final DonationPointRepository donationPointRepository;

  public CreateDonationPointService(CreateUserService createUserService, DonationPointRepository donationPointRepository) {
    this.donationPointRepository = donationPointRepository;
    this.createUserService = createUserService;
  }

  public DonationPoint create(CreateUserDto user, String description){
    var newUser = new User(user);
    newUser.setUserType(UserType.DONATION_POINT);

    this.createUserService.save(newUser);

    var donationPoint = new DonationPoint();
    donationPoint.setUser(newUser);
    donationPoint.setDescription(description);

    this.donationPointRepository.save(donationPoint);

    return donationPoint;
  }
}
