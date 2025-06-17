package com.doanet.api.service;

import com.doanet.api.dto.CreateUserDto;
import com.doanet.api.entity.Donor;
import com.doanet.api.entity.User;
import com.doanet.api.enums.UserType;
import com.doanet.api.repository.DonorRepository;
import com.doanet.api.repository.UserRepository;
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
    var newUser = new User(user);
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
