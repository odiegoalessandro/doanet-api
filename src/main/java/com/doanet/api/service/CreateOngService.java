package com.doanet.api.service;

import com.doanet.api.dto.CreateUserDto;
import com.doanet.api.entity.Ong;
import com.doanet.api.entity.User;
import com.doanet.api.enums.UserType;
import com.doanet.api.repository.OngRepository;
import com.doanet.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateOngService {
  private final CreateUserService createUserService;
  private final OngRepository ongRepository;

  public CreateOngService(CreateUserService createUserService, OngRepository ongRepository){
    this.createUserService = createUserService;
    this.ongRepository = ongRepository;
  }

  public Ong create(CreateUserDto user, String cnpj){
    var newUser = new User(user);
    newUser.setUserType(UserType.ONG);
    this.createUserService.save(newUser);

    var ong = new Ong();
    ong.setUser(newUser);
    ong.setCnpj(cnpj);

    this.ongRepository.save(ong);

    return ong;
  }
}
