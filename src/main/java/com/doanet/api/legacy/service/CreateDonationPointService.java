package com.doanet.api.legacy.service;

import com.doanet.api.legacy.dto.CreateUserDto;
import com.doanet.api.legacy.entity.DonationPointEntity;
import com.doanet.api.infra.persistence.UserEntity;
import com.doanet.api.legacy.enums.UserType;
import com.doanet.api.legacy.repository.DonationPointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateDonationPointService {
  private final CreateUserService createUserService;
  private final DonationPointRepository donationPointRepository;

  public CreateDonationPointService(
    CreateUserService createUserService,
    DonationPointRepository donationPointRepository
  ) {
    this.donationPointRepository = donationPointRepository;
    this.createUserService = createUserService;
  }

  @Transactional
  public DonationPointEntity create(CreateUserDto user, String description){
    var newUser = new UserEntity(user);
    newUser.setUserType(UserType.DONATION_POINT);

    this.createUserService.save(newUser);

    var donationPoint = new DonationPointEntity();
    donationPoint.setUser(newUser);
    donationPoint.setDescription(description);

    this.donationPointRepository.save(donationPoint);

    return donationPoint;
  }
}
