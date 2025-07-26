package com.doanet.api.config;

import com.doanet.api.application.gateways.GetCoordinatesByAddress;
import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.application.usecases.user.CreateUserUseCase;
import com.doanet.api.infra.gateways.UserEntityMapper;
import com.doanet.api.infra.gateways.UserRepositoryImpl;
import com.doanet.api.infra.persistence.JpaUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
  @Bean
  public CreateUserUseCase createUserUseCase(
    UserRepository userRepository,
    GetCoordinatesByAddress getCoordinatesByAddress
  ) {
    return new CreateUserUseCase(userRepository, getCoordinatesByAddress);
  }

  @Bean
  public UserRepositoryImpl userRepositoryImpl(JpaUserRepository jpaUserRepository, UserEntityMapper userEntityMapper) {
    return new UserRepositoryImpl(jpaUserRepository, userEntityMapper);
  }
}
