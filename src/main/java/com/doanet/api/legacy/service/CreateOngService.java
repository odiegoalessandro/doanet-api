package com.doanet.api.legacy.service;

import com.doanet.api.legacy.dto.CreateUserDto;
import com.doanet.api.legacy.entity.Ong;
import com.doanet.api.infra.persistence.UserEntity;
import com.doanet.api.legacy.enums.UserType;
import com.doanet.api.legacy.repository.OngRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CreateOngService {
  private final CreateUserService createUserService;
  private final OngRepository ongRepository;

  public CreateOngService(CreateUserService createUserService, OngRepository ongRepository){
    this.createUserService = createUserService;
    this.ongRepository = ongRepository;
  }

  @Transactional
  public Ong create(CreateUserDto user, String cnpj){
    log.info(user.toString());
    var newUser = new UserEntity(user);
    newUser.setUserType(UserType.ONG);
    this.createUserService.save(newUser);
    log.info(newUser.toString());

    var ong = new Ong();
    ong.setUser(newUser);
    ong.setCnpj(cnpj);

    this.ongRepository.save(ong);

    return ong;
  }
}
