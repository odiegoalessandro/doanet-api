package com.doanet.api.service;

import com.doanet.api.dto.CreateUserDto;
import com.doanet.api.entity.Donor;
import com.doanet.api.entity.User;
import com.doanet.api.enums.UserType;
import com.doanet.api.repository.DonorRepository;
import com.doanet.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateDonorService {
  private final UserRepository userRepository;
  private final DonorRepository donorRepository;

  public CreateDonorService(UserRepository userRepository, DonorRepository donorRepository){
    this.userRepository = userRepository;
    this.donorRepository = donorRepository;
  }

  public Donor create(CreateUserDto user, String document, String reasonSocial) {
    var newUser = new User(user);
    newUser.setUserType(UserType.DONOR);

    this.userRepository.save(newUser);

    var donor = new Donor();
    donor.setUser(newUser);
    donor.setDocument(document);
    donor.setReasonSocial(reasonSocial);

    donorRepository.save(donor);

    return donor;
  }
}
