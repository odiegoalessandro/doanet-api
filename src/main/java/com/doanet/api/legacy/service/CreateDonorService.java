package com.doanet.api.legacy.service;

import com.doanet.api.legacy.dto.CreateUserDto;
import com.doanet.api.legacy.entity.Donor;
import com.doanet.api.infra.persistence.UserEntity;
import com.doanet.api.legacy.enums.UserType;
import com.doanet.api.legacy.repository.DonorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateDonorService {
  private final CreateUserService createUserService;
  private final DonorRepository donorRepository;

  public CreateDonorService(CreateUserService createUserService, DonorRepository donorRepository){
    this.createUserService = createUserService;
    this.donorRepository = donorRepository;
  }

  @Transactional
  public Donor create(CreateUserDto user, String document, String reasonSocial) {
    var newUser = new UserEntity(user);
    newUser.setUserType(UserType.DONOR);

    this.createUserService.save(newUser);

    var donor = new Donor();
    donor.setUser(newUser);
    donor.setDocument(document);
    donor.setReasonSocial(reasonSocial);

    donorRepository.save(donor);

    return donor;
  }
}
